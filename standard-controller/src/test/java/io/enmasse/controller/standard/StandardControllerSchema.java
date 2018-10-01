/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.controller.standard;

import io.enmasse.address.model.*;
import io.enmasse.admin.model.v1.*;
import io.enmasse.config.AnnotationKeys;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StandardControllerSchema {

    private AddressSpacePlan plan;
    private AddressSpaceType type;
    private Schema schema;

    public StandardControllerSchema() {
        this(Arrays.asList(new ResourceAllowance("router", 0.0, 1.0),
                new ResourceAllowance("broker", 0.0, 3.0),
                new ResourceAllowance("aggregate", 0.0, 3.0)));

    }

    public StandardControllerSchema(List<ResourceAllowance> resourceAllowanceList) {
        plan = new AddressSpacePlan.Builder()
                .setMetadata(new ObjectMetadata.Builder()
                        .setName("plan1")
                        .setAnnotations(Collections.singletonMap(AnnotationKeys.DEFINED_BY, "cfg1"))
                        .build())
                .setResources(resourceAllowanceList)
                .setAddressSpaceType("standard")
                .setAddressPlans(Arrays.asList(
                        "small-anycast",
                        "small-queue",
                        "pooled-queue-larger",
                        "pooled-queue-small",
                        "pooled-queue-tiny",
                        "small-topic",
                        "small-subscription"
                ))
                .build();

        type = new AddressSpaceType.Builder()
                .setName("standard")
                .setDescription("standard")
                .setAddressSpacePlans(Arrays.asList(plan))
                .setAvailableEndpoints(Collections.singletonList(new EndpointSpec.Builder()
                        .setName("messaging")
                        .setService("messaging")
                        .setServicePort("amqps")
                        .build()))
                .setAddressTypes(Arrays.asList(
                        new AddressType.Builder()
                                .setName("anycast")
                                .setDescription("anycast")
                                .setAddressPlans(Arrays.asList(
                                        new AddressPlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder().setName("small-anycast").build())
                                        .setAddressType("anycast")
                                        .setRequiredResources(Arrays.asList(
                                                new ResourceRequest("router", 0.2000000000)))
                                        .build()))
                                .build(),
                        new AddressType.Builder()
                                .setName("queue")
                                .setDescription("queue")
                                .setAddressPlans(Arrays.asList(
                                        new AddressPlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder().setName("pooled-queue-large").build())
                                                .setAddressType("queue")
                                                .setRequiredResources(Arrays.asList(
                                                        new ResourceRequest("broker", 0.6)))
                                                .build(),
                                        new AddressPlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder().setName("pooled-queue-small").build())
                                                .setAddressType("queue")
                                                .setRequiredResources(Arrays.asList(
                                                        new ResourceRequest("broker", 0.1)))
                                                .build(),
                                        new AddressPlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder().setName("pooled-queue-tiny").build())
                                                .setAddressType("queue")
                                                .setRequiredResources(Arrays.asList(
                                                        new ResourceRequest("broker", 0.049)))
                                                .build(),
                                        new AddressPlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder().setName("small-queue").build())
                                                .setAddressType("queue")
                                                .setRequiredResources(Arrays.asList(
                                                        new ResourceRequest("router", 0.2),
                                                        new ResourceRequest("broker", 0.4)))
                                                .build(),
                                        new AddressPlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder().setName("large-queue").build())
                                                .setAddressType("queue")
                                                .setRequiredResources(Arrays.asList(
                                                        new ResourceRequest("router", 0.2),
                                                        new ResourceRequest("broker", 1.0)))
                                                .build(),
                                        new AddressPlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder().setName("xlarge-queue").build())
                                                .setAddressType("queue")
                                                .setRequiredResources(Arrays.asList(
                                                        new ResourceRequest("router", 0.2),
                                                        new ResourceRequest("broker", 2.0)))
                                                .build()))
                                .build(),
                        new AddressType.Builder()
                                .setName("topic")
                                .setDescription("topic")
                                .setAddressPlans(Arrays.asList(
                                        new AddressPlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder().setName("small-topic").build())
                                                .setAddressType("topic")
                                                .setRequiredResources(Arrays.asList(
                                                        new ResourceRequest("router", 0.1),
                                                        new ResourceRequest("broker", 0.2)))
                                                .build(),
                                        new AddressPlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder().setName("xlarge-topic").build())
                                                .setAddressType("topic")
                                                .setRequiredResources(Arrays.asList(
                                                        new ResourceRequest("router", 0.1),
                                                        new ResourceRequest("broker", 1.0)))
                                                .build()))
                                .build(),
                        new AddressType.Builder()
                                .setName("subscription")
                                .setDescription("subscription")
                                .setAddressPlans(Arrays.asList(
                                        new AddressPlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder().setName("small-subscription").build())
                                                .setAddressType("subscription")
                                                .setRequiredResources(Arrays.asList(
                                                        new ResourceRequest("router", 0.05),
                                                        new ResourceRequest("broker", 0.1)))
                                                .build()))
                                .build()))
                .setInfraConfigs(Arrays.asList(new StandardInfraConfig(new ObjectMetadata.Builder()
                        .setName("cfg1")
                        .setAnnotations(Collections.singletonMap(AnnotationKeys.QUEUE_TEMPLATE_NAME, "queuetemplate"))
                        .build(), new StandardInfraConfigSpec("latest",
                        new StandardInfraConfigSpecAdmin(
                                new StandardInfraConfigSpecAdminResources("512Mi")),
                        new StandardInfraConfigSpecBroker(
                                new StandardInfraConfigSpecBrokerResources("512Mi", "2Gi"),
                                "FAIL"),
                        new StandardInfraConfigSpecRouter(
                                new StandardInfraConfigSpecRouterResources("512Mi"),
                                "500")))))
                .setInfraConfigType(json -> null)
                .build();

        schema = new Schema.Builder()
                .setAddressSpaceTypes(Arrays.asList(type))
                .build();
    }

    public AddressSpacePlan getPlan() {
        return plan;
    }

    public AddressSpaceType getType() {
        return type;
    }

    public Schema getSchema() {
        return schema;
    }
}
