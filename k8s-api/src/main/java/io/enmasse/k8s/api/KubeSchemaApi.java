/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.enmasse.address.model.*;
import io.enmasse.admin.model.v1.*;
import io.enmasse.config.AnnotationKeys;
import io.enmasse.config.LabelKeys;
import io.enmasse.k8s.api.cache.*;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.RequestConfig;
import io.fabric8.kubernetes.client.RequestConfigBuilder;
import io.fabric8.openshift.client.NamespacedOpenShiftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class KubeSchemaApi implements SchemaApi, AddressSpacePlanWatcher, AddressPlanWatcher, BrokeredInfraConfigWatcher, StandardInfraConfigWatcher

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(KubeSchemaApi.class);
    private final NamespacedOpenShiftClient client;
    private final AddressSpacePlanApi addressSpacePlanApi;
    private final String namespace;

    private volatile Set<AddressSpacePlan> currentAddressSpacePlans;
    private volatile Set<AddressPlan> currentAddressPlans;
    private volatile Set<StandardInfraConfig> currentStandardInfraConfigs;
    private volatile Set<BrokeredInfraConfig> currentBrokeredInfraConfigs;

    public KubeSchemaApi(NamespacedOpenShiftClient client, String namespace) {
        this.client = client;
        this.namespace = namespace;
    }

    private void validateAddressSpacePlan(AddressSpacePlan addressSpacePlan, List<AddressPlan> addressPlans, List<String> infraTemplateNames) {
        String definedBy = addressSpacePlan.getMetadata().getAnnotations().get(AnnotationKeys.DEFINED_BY);
        if (!infraTemplateNames.contains(definedBy)) {
            String error = "Error validating address space plan " + addressSpacePlan.getMetadata().getName() + ": missing infra config definition " + definedBy + ", found: " + infraTemplateNames;
            log.warn(error);
            throw new SchemaValidationException(error);
        }

        Set<String> addressPlanNames = addressPlans.stream().map(p -> p.getMetadata().getName()).collect(Collectors.toSet());
        if (!addressPlanNames.containsAll(addressSpacePlan.getAddressPlans())) {
            Set<String> missing = new HashSet<>(addressSpacePlan.getAddressPlans());
            missing.removeAll(addressPlanNames);
            String error = "Error validating address space plan " + addressSpacePlan.getMetadata().getName() + ": missing " + missing;
            log.warn(error);
            throw new SchemaValidationException(error);
        }
    }

    private void validateAddressPlan(AddressPlan addressPlan) {
        Set<String> allowedResources = new HashSet<>(Arrays.asList("broker", "router"));
        Set<String> resourcesUsed = addressPlan.getRequiredResources().stream().map(ResourceRequest::getName).collect(Collectors.toSet());

        if (!allowedResources.containsAll(resourcesUsed)) {
            Set<String> missing = new HashSet<>(resourcesUsed);
            missing.removeAll(allowedResources);
            String error = "Error validating address plan " + addressPlan.getMetadata().getName() + ": missing resources " + missing;
            log.warn(error);
            throw new SchemaValidationException(error);
        }
    }

    private EndpointSpec createEndpointSpec(String name, String port) {
        return new EndpointSpec.Builder()
                .setName(name)
                .setService(name)
                .setServicePort(port)
                .build();
    }

    private AddressSpaceType createStandardType(List<AddressSpacePlan> addressSpacePlans, List<AddressPlan> addressPlans, List<InfraConfig> standardInfraConfigs) {
        AddressSpaceType.Builder builder = new AddressSpaceType.Builder();
        builder.setName("standard");
        builder.setDescription("A standard address space consists of an AMQP router network in combination with " +
                "attachable 'storage units'. The implementation of a storage unit is hidden from the client " +
                        "and the routers with a well defined API.");

        builder.setAvailableEndpoints(Arrays.asList(
                createEndpointSpec("messaging", "amqps"),
                new EndpointSpec.Builder()
                        .setName("amqp-wss")
                        .setService("messaging")
                        .setServicePort("https")
                        .setCertSpec(new CertSpec(null, null))
                        .build(),
                createEndpointSpec("mqtt", "secure-mqtt"),
                createEndpointSpec("console", "https")));

        List<AddressSpacePlan> filteredAddressSpaceplans = addressSpacePlans.stream()
                .filter(plan -> "standard".equals(plan.getAddressSpaceType()))
                .collect(Collectors.toList());
        builder.setAddressSpacePlans(filteredAddressSpaceplans);

        List<AddressPlan> filteredAddressPlans = addressPlans.stream()
                .filter(plan -> filteredAddressSpaceplans.stream()
                        .filter(aPlan -> aPlan.getAddressPlans().contains(plan.getMetadata().getName()))
                        .count() > 0)
                .collect(Collectors.toList());


        builder.setInfraConfigs(standardInfraConfigs);
        builder.setInfraConfigType(json -> mapper.readValue(json, StandardInfraConfig.class));

        builder.setAddressTypes(Arrays.asList(
                createAddressType(
                        "anycast",
                        "A direct messaging address type. Messages sent to an anycast address are not " +
                                "stored but forwarded directly to a consumer.",
                        filteredAddressPlans),
                createAddressType(
                        "multicast",
                        "A direct messaging address type. Messages sent to a multicast address are not " +
                                "stored but forwarded directly to multiple consumers.",
                        filteredAddressPlans),
                createAddressType(
                        "queue",
                        "A store-and-forward queue. A queue may be sharded across multiple storage units, " +
                                "in which case message ordering is no longer guaranteed.",
                        filteredAddressPlans),
                createAddressType(
                        "topic",
                        "A topic address for store-and-forward publish-subscribe messaging. Each message published " +
                                "to a topic address is forwarded to all subscribes on that address.",
                        filteredAddressPlans),
                createAddressType(
                        "subscription",
                        "A subscription on a topic",
                        filteredAddressPlans)));

        return builder.build();
    }

    private AddressSpaceType createBrokeredType(List<AddressSpacePlan> addressSpacePlans, List<AddressPlan> addressPlans, List<InfraConfig> brokeredInfraConfigs) {
        AddressSpaceType.Builder builder = new AddressSpaceType.Builder();
        builder.setName("brokered");
        builder.setDescription("A brokered address space consists of a broker combined with a console for managing addresses.");

        builder.setAvailableEndpoints(Arrays.asList(
                createEndpointSpec("messaging", "amqps"),
                createEndpointSpec("console", "https")));

        List<AddressSpacePlan> filteredAddressSpaceplans = addressSpacePlans.stream()
                .filter(plan -> "brokered".equals(plan.getAddressSpaceType()))
                .collect(Collectors.toList());
        builder.setAddressSpacePlans(filteredAddressSpaceplans);

        List<AddressPlan> filteredAddressPlans = addressPlans.stream()
                .filter(plan -> filteredAddressSpaceplans.stream()
                        .filter(aPlan -> aPlan.getAddressPlans().contains(plan.getMetadata().getName()))
                        .count() > 0)
                .collect(Collectors.toList());

        builder.setInfraConfigs(brokeredInfraConfigs);
        builder.setInfraConfigType(json -> mapper.readValue(json, BrokeredInfraConfig.class));

        builder.setAddressTypes(Arrays.asList(
                createAddressType(
                        "queue",
                        "A queue that supports selectors, message grouping and transactions",
                        filteredAddressPlans),
                createAddressType(
                    "topic",
                    "A topic supports pub-sub semantics. Messages sent to a topic address is forwarded to all subscribes on that address.",
                    filteredAddressPlans)));

        return builder.build();
    }

    private AddressType createAddressType(String name, String description, List<AddressPlan> addressPlans) {
        AddressType.Builder builder = new AddressType.Builder();
        builder.setAddressPlans(addressPlans.stream()
                .filter(plan -> plan.getAddressType().equals(name))
                .collect(Collectors.toList()));
        builder.setName(name);
        builder.setDescription(description);
        return builder.build();
    }

    @Override
    public Watch watchSchema(Watcher<Schema> watcher, Duration resyncInterval) {
        Watch addressSpacePlanWatch = addressSpacePlanApi.watchAddressSpacePlans(this, resyncInterval);
        return new Watch() {
            @Override
            public void close() throws Exception {
                Exception e = null;
                try {
                    addressSpacePlanWatch.close();
                } catch (Exception ex) {
                    e = ex;
                }
                if (e != null) {
                    throw e;
                }
            }
        }
    }

    @Override
    public void onUpdate(AddressSpacePlanList planList) {
    }

    @Override
    public io.fabric8.kubernetes.client.Watch watch(io.fabric8.kubernetes.client.Watcher<ConfigMap> watcher, ListOptions listOptions) {
        RequestConfig requestConfig = new RequestConfigBuilder()
                .withRequestTimeout(listOptions.getTimeoutSeconds())
                .build();
        return client.withRequestConfig(requestConfig).call(c ->
                c.configMaps()
                        .inNamespace(namespace)
                        .withLabelIn(LabelKeys.TYPE,"address-space-plan", "address-plan", "brokered-infra-config", "standard-infra-config")
                        .withResourceVersion(listOptions.getResourceVersion())
                        .watch(watcher));
    }

    private Schema assembleSchema(List<AddressSpacePlan> addressSpacePlans, List<AddressPlan> addressPlans, List<StandardInfraConfig> standardInfraConfigs, List<BrokeredInfraConfig> brokeredInfraConfigs) {
        log.info("Got brokered infra configs: {}", brokeredInfraConfigs);
        log.info("Got standard infra configs: {}", standardInfraConfigs);

        for (AddressSpacePlan addressSpacePlan : addressSpacePlans) {
            if (addressSpacePlan.getAddressSpaceType().equals("brokered")) {
                validateAddressSpacePlan(addressSpacePlan, addressPlans, brokeredInfraConfigs.stream().map(t -> t.getMetadata().getName()).collect(Collectors.toList()));
            } else {
                validateAddressSpacePlan(addressSpacePlan, addressPlans, standardInfraConfigs.stream().map(t -> t.getMetadata().getName()).collect(Collectors.toList()));
            }
        }

        for (AddressPlan addressPlan : addressPlans) {
            validateAddressPlan(addressPlan);
        }

        List<AddressSpaceType> types = new ArrayList<>();
        types.add(createBrokeredType(addressSpacePlans, addressPlans, new ArrayList<>(brokeredInfraConfigs)));
        types.add(createStandardType(addressSpacePlans, addressPlans, new ArrayList<>(standardInfraConfigs)));
        return new Schema.Builder()
                .setAddressSpaceTypes(types)
                .build();
    }
}
