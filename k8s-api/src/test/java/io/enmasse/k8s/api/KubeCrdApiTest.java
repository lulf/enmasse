/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import io.enmasse.admin.model.v1.AddressSpacePlan;
import io.enmasse.admin.model.v1.AddressSpacePlanList;
import io.enmasse.admin.model.v1.AdminCrd;
import io.enmasse.admin.model.v1.DoneableAddressSpacePlan;
import io.enmasse.k8s.util.JULInitializingTest;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.openshift.client.NamespacedOpenShiftClient;
import io.fabric8.openshift.client.server.mock.OpenShiftServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KubeCrdApiTest extends JULInitializingTest {

    private OpenShiftServer openShiftServer = new OpenShiftServer(false, true);

    @BeforeEach
    void setUp() {
        openShiftServer.before();
    }

    @AfterEach
    void tearDown() {
        openShiftServer.after();
    }

    @Test
    void testNotifiesExisting() throws Exception {
        NamespacedOpenShiftClient client = openShiftServer.getOpenshiftClient();
        CustomResourceDefinition crd = AdminCrd.addressSpacePlans();
        CrdApi<AddressSpacePlan> addressSpacePlanApi = new KubeCrdApi<>(client, client.getNamespace(), crd,
                AddressSpacePlan.class,
                AddressSpacePlanList.class,
                DoneableAddressSpacePlan.class);

        client.customResources(crd, AddressSpacePlan.class, AddressSpacePlanList.class, DoneableAddressSpacePlan.class)
                .createNew()
                .withNewMetadata()
                .withName("plan1")
                .withNamespace(client.getNamespace())
                .endMetadata()

                .withAddressSpaceType("standard")
                .withAddressPlans(Arrays.asList("p1", "p2"))
                .done();

        CompletableFuture<List<AddressSpacePlan>> promise = new CompletableFuture<>();
        try (Watch watch = addressSpacePlanApi.watchResources(items -> {
            if (!items.isEmpty()) {
                promise.complete(items);
            }
        }, Duration.ofMinutes(1))) {
            List<AddressSpacePlan> list = promise.get(30, TimeUnit.SECONDS);
            assertEquals(1, list.size());
            assertEquals("plan1", list.get(0).getMetadata().getName());
        }
    }

    @Test
    void testNotifiesCreated() throws Exception {
        NamespacedOpenShiftClient client = openShiftServer.getOpenshiftClient();
        CustomResourceDefinition crd = AdminCrd.addressSpacePlans();
        CrdApi<AddressSpacePlan> addressSpacePlanApi = new KubeCrdApi<>(client, client.getNamespace(), crd,
                AddressSpacePlan.class,
                AddressSpacePlanList.class,
                DoneableAddressSpacePlan.class);

        CompletableFuture<List<AddressSpacePlan>> promise = new CompletableFuture<>();
        try (Watch watch = addressSpacePlanApi.watchResources(items -> {
            if (!items.isEmpty()) {
                promise.complete(items);
            }

        }, Duration.ofSeconds(2))) {
            client.customResources(crd, AddressSpacePlan.class, AddressSpacePlanList.class, DoneableAddressSpacePlan.class)
                    .createNew()
                    .withNewMetadata()
                    .withName("plan1")
                    .withNamespace(client.getNamespace())
                    .endMetadata()

                    .withAddressSpaceType("standard")
                    .withAddressPlans(Arrays.asList("p1", "p2"))
                    .done();

            List<AddressSpacePlan> list = promise.get(30, TimeUnit.SECONDS);
            assertEquals(1, list.size());
            assertEquals("plan1", list.get(0).getMetadata().getName());
        }
    }
}
