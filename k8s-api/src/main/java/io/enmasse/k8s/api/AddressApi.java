/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import io.enmasse.address.model.Address;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * API for managing addresses in kubernetes.
 */
public interface AddressApi {
    Optional<Address> getAddressWithName(String namespace, String name);
    Set<Address> listAddresses(String namespace);
    Set<Address> listAddressesWithLabels(String namespace, Map<String, String> labels);
    void deleteAddresses(String namespace);

    void createAddress(Address address);
    boolean replaceAddress(Address address);
    boolean deleteAddress(Address address);

    Watch watchAddresses(Watcher<Address> watcher, Duration resyncInterval) throws Exception;
}
