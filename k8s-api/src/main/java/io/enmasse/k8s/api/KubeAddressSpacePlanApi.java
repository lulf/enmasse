/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import io.enmasse.admin.model.v1.AddressSpacePlan;
import io.enmasse.admin.model.v1.AddressSpacePlanList;
import io.enmasse.admin.model.v1.DoneableAddressSpacePlan;
import io.enmasse.k8s.api.cache.*;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.kubernetes.client.RequestConfig;
import io.fabric8.kubernetes.client.RequestConfigBuilder;
import io.fabric8.openshift.client.NamespacedOpenShiftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;
import java.util.HashSet;

/**
 * Implements the AddressApi using config maps.
 */
public class KubeAddressSpacePlanApi implements AddressSpacePlanApi, ListerWatcher<AddressSpacePlan, AddressSpacePlanList> {

    private static final Logger log = LoggerFactory.getLogger(KubeAddressSpacePlanApi.class);
    private final NamespacedOpenShiftClient client;
    private final CustomResourceDefinition addressSpacePlanDefinition;

    public KubeAddressSpacePlanApi(NamespacedOpenShiftClient client, CustomResourceDefinition addressSpacePlanDefinition) {
        this.client = client;
        this.addressSpacePlanDefinition = addressSpacePlanDefinition;
    }

    @Override
    public AddressSpacePlanList list(ListOptions listOptions) {
        return client.customResources(addressSpacePlanDefinition, AddressSpacePlan.class, AddressSpacePlanList.class, DoneableAddressSpacePlan.class).list();
    }

    @Override
    public io.fabric8.kubernetes.client.Watch watch(io.fabric8.kubernetes.client.Watcher<AddressSpacePlan> watcher, ListOptions listOptions) {
        RequestConfig requestConfig = new RequestConfigBuilder()
                .withRequestTimeout(listOptions.getTimeoutSeconds())
                .build();
        return client.withRequestConfig(requestConfig).call(c ->
                c.customResources(addressSpacePlanDefinition, AddressSpacePlan.class, AddressSpacePlanList.class, DoneableAddressSpacePlan.class).withResourceVersion(listOptions.getResourceVersion()).watch(watcher));
    }

    @Override
    public Watch watchAddressSpacePlans(Watcher<AddressSpacePlanList> watcher, Duration resyncInterval) {
        WorkQueue<AddressSpacePlan> queue = new FifoQueue<>(config -> config.getMetadata().getName());
        Reflector.Config<AddressSpacePlan, AddressSpacePlanList> config = new Reflector.Config<>();
        config.setClock(Clock.systemUTC());
        config.setExpectedType(AddressSpacePlan.class);
        config.setListerWatcher(this);
        config.setResyncInterval(resyncInterval);
        config.setWorkQueue(queue);
        config.setProcessor(map -> {
            if (queue.hasSynced()) {
                AddressSpacePlanList list = new AddressSpacePlanList();
                list.setItems(queue.list());
                watcher.onUpdate(list);
            }
        });

        Reflector<AddressSpacePlan, AddressSpacePlanList> reflector = new Reflector<>(config);
        Controller controller = new Controller(reflector);
        controller.start();
        return controller;
    }
}
