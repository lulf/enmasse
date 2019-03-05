/*
 * Copyright 2019, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import io.enmasse.address.model.Schema;
import io.enmasse.admin.model.v1.AuthenticationService;

import java.util.Optional;

/**
 * Interface for resolving different authentication service
 */
public class SchemaAuthenticationServiceRegistry implements AuthenticationServiceRegistry {

    private final SchemaProvider schemaProvider;

    public SchemaAuthenticationServiceRegistry(SchemaProvider schemaProvider) {
        this.schemaProvider = schemaProvider;
    }

    @Override
    public Optional<AuthenticationService> findAuthenticationService(io.enmasse.address.model.AuthenticationService authenticationService) {
        Schema schema = schemaProvider.getSchema();
        if (authenticationService.getName() == null) {
            return schema.findAuthenticationService(authenticationService.getType().getName());
        } else {
            return schema.findAuthenticationService(authenticationService.getName());
        }
    }

    @Override
    public Optional<AuthenticationService> resolveDefaultAuthenticationService() {
        Schema schema = schemaProvider.getSchema();
        AuthenticationService standard = schema.findAuthenticationService("standard").orElse(null);
        if (standard == null) {
            return schema.findAuthenticationService("none");
        } else {
            return Optional.of(standard);
        }
    }
}
