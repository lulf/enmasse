/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import io.enmasse.address.model.*;
import io.enmasse.admin.model.v1.*;
import io.enmasse.config.AnnotationKeys;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

public class TestSchemaApi implements SchemaApi {
    public Schema getSchema() {
        return new Schema.Builder()
                .setAddressSpaceTypes(Collections.singletonList(
                        new AddressSpaceType.Builder()
                                .setName("type1")
                                .setDescription("Test Type")
                                .setAvailableEndpoints(Collections.singletonList(new EndpointSpec.Builder()
                                        .setName("messaging")
                                        .setService("messaging")
                                        .setServicePort("amqps")
                                        .build()))
                                .setAddressTypes(Arrays.asList(
                                        new AddressType.Builder()
                                                .setName("anycast")
                                                .setDescription("Test direct")
                                                .setAddressPlans(Arrays.asList(
                                                        new AddressPlan.Builder()
                                                                .setMetadata(new ObjectMetadata.Builder()
                                                                        .setName("plan1")
                                                                        .build())
                                                                .setAddressType("anycast")
                                                                .setRequiredResources(Collections.singletonList(
                                                                        new ResourceRequest("router", 1.0)
                                                                ))
                                                                .build()
                                                ))
                                                .build(),
                                        new AddressType.Builder()
                                                .setName("queue")
                                                .setDescription("Test queue")
                                                .setAddressPlans(Arrays.asList(
                                                        new AddressPlan.Builder()
                                                                .setMetadata(new ObjectMetadata.Builder()
                                                                        .setName("pooled-inmemory")
                                                                        .build())
                                                                .setAddressType("queue")
                                                                .setRequiredResources(Collections.singletonList(
                                                                        new ResourceRequest("broker", 0.1)
                                                                ))
                                                                .build(),
                                                        new AddressPlan.Builder()
                                                                .setMetadata(new ObjectMetadata.Builder()
                                                                        .setName("plan1")
                                                                        .build())
                                                                .setAddressType("queue")
                                                                .setRequiredResources(Collections.singletonList(
                                                                        new ResourceRequest("broker", 1.0)
                                                                ))
                                                                .build()
                                                ))
                                                .build()
                                ))
                                .setInfraConfigs(Arrays.asList((InfraConfig) () -> new ObjectMetadata.Builder()
                                        .setName("infra")
                                        .build()))
                                .setInfraConfigType(json -> null)
                                .setAddressSpacePlans(Collections.singletonList(
                                        new AddressSpacePlan.Builder()
                                                .setMetadata(new ObjectMetadata.Builder()
                                                        .setAnnotations(Collections.singletonMap(AnnotationKeys.DEFINED_BY, "infra"))
                                                        .setName("myplan")
                                                        .build())
                                                .setAddressSpaceType("type1")
                                                .setResources(Collections.singletonList(
                                                        new ResourceAllowance("broker", 0.0, 1.0)
                                                ))
                                                .setAddressPlans(Collections.singletonList("plan1"))
                                                .build()
                                ))
                                .build()

                ))
                .build();
    }

    @Override
    public Watch watchSchema(Watcher<Schema> schemaStore, Duration resyncInterval) throws Exception {
        return null;
    }

}
