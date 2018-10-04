/*
 * Copyright 2018, Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.operator.model;

import io.enmasse.operator.model.Enmasse;
import io.fabric8.kubernetes.api.model.Doneable;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinitionBuilder;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.client.CustomResourceDoneable;
import io.fabric8.kubernetes.client.CustomResourceList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.internal.KubernetesDeserializer;
import io.strimzi.api.kafka.model.DoneableKafka;
import io.strimzi.api.kafka.model.DoneableKafkaConnect;
import io.strimzi.api.kafka.model.DoneableKafkaConnectS2I;
import io.strimzi.api.kafka.model.DoneableKafkaTopic;
import io.strimzi.api.kafka.model.DoneableKafkaUser;
import io.strimzi.api.kafka.model.DoneableKafkaMirrorMaker;
import io.strimzi.api.kafka.model.Kafka;
import io.strimzi.api.kafka.model.KafkaConnect;
import io.strimzi.api.kafka.model.KafkaConnectS2I;
import io.strimzi.api.kafka.model.KafkaMirrorMaker;
import io.strimzi.api.kafka.model.KafkaTopic;
import io.strimzi.api.kafka.model.KafkaUser;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * "Static" information about the CRDs defined in this package
 */
public class Crds {

    public static final String CRD_KIND = "CustomResourceDefinition";

    private static final Class<? extends CustomResource>[] CRDS = new Class[] {
        Enmasse.class,
    };

    private Crds() {
    }

    /**
     * Register custom resource kinds with {@link KubernetesDeserializer} so Fabric8 knows how to deserialize them.
     */
    public static void registerCustomKinds() {
        for (Class<? extends CustomResource> c : CRDS) {
            KubernetesDeserializer.registerCustomKind(kind(c), c);
        }
    }

    private static CustomResourceDefinition crd(Class<? extends CustomResource> cls) {
        String kind;
        String crdApiVersion;
        String plural;
        String listKind;
        String singular;
        String version;
        String group;
        List<String> shortNames = emptyList();
        if (cls.equals(Enmasse.class)) {
            kind = Enmasse.RESOURCE_KIND;
            crdApiVersion = Enmasse.CRD_API_VERSION;
            plural = Enmasse.RESOURCE_PLURAL;
            singular = Enmasse.RESOURCE_SINGULAR;
            listKind = Enmasse.RESOURCE_LIST_KIND;
            group = Enmasse.RESOURCE_GROUP;
            version = Enmasse.VERSION;
        } else {
            throw new RuntimeException();
        }
        return new CustomResourceDefinitionBuilder()
                .withApiVersion(crdApiVersion)
                .withKind(CRD_KIND)
                .withNewMetadata()
                    .withName(plural + "." + group)
                .endMetadata()
                .withNewSpec()
                    .withGroup(group)
                    .withVersion(version)
                    .withNewNames()
                        .withKind(kind)
                        .withListKind(listKind)
                        .withPlural(plural)
                        .withSingular(singular)
                        .withShortNames(shortNames)
                    .endNames()
                .endSpec()
                .build();
    }

    public static CustomResourceDefinition enmasse() {
        return crd(Enmasse.class);
    }

    public static MixedOperation<Enmasse, EnmasseList, DoneableEnmasse, Resource<Enmasse, DoneableEnmasse>> enmasseOperation(KubernetesClient client) {
        return client.customResources(enmasse(), Enmasse.class, EnmasseList.class, DoneableEnmasse.class);
    }

    public static <T extends CustomResource, L extends CustomResourceList<T>, D extends Doneable<T>> MixedOperation<T, L, D, Resource<T, D>>
            operation(KubernetesClient client,
                Class<T> cls,
                Class<L> listCls,
                Class<D> doneableCls) {
        return client.customResources(crd(cls), cls, listCls, doneableCls);
    }

    public static <T extends CustomResource> String kind(Class<T> cls) {
        try {
            return cls.newInstance().getKind();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
