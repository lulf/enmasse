/*
 * Copyright 2016-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.controller;

import io.enmasse.address.model.AuthenticationServiceRegistry;
import io.enmasse.admin.model.v1.AuthenticationService;

import java.util.Optional;

public class KubeAuthenticationServiceRegistry implements AuthenticationServiceRegistry {
    private final
    @Override
    public Optional<AuthenticationService> findAuthenticationService(io.enmasse.address.model.AuthenticationService authenticationService) {
        return Optional.empty();
    }
}
