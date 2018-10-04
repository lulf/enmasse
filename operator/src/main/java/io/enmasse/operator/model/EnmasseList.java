/*
 * Copyright 2018, Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.operator.model;

import io.fabric8.kubernetes.client.CustomResourceList;

/**
 * A {@code CustomResourceList<KafkaAssembly>} required for using Fabric8 CRD support.
 */
public class EnmasseList extends CustomResourceList<Enmasse> {
    private static final long serialVersionUID = 1L;
}
