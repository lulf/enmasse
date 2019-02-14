/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.controller.common;

import io.enmasse.address.model.AddressSpace;
import io.enmasse.address.model.AuthenticationService;
import io.enmasse.address.model.AuthenticationServiceRegistry;
import io.enmasse.config.AnnotationKeys;

import java.util.Optional;

/**
 * Resolves the none authentication service host name.
 */
public class NoneAuthenticationServiceRegistry implements AuthenticationServiceRegistry {
    private final String host;
    private final int port;

    public NoneAuthenticationServiceRegistry(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String getHost(AuthenticationService authService) {
        return host;
    }

    @Override
    public int getPort(AuthenticationService authService) {
        return port;
    }

    @Override
    public Optional<String> getCaSecretName(AuthenticationService authService) {
        return Optional.of("none-authservice-cert");
    }

    @Override
    public Optional<String> getClientSecretName(AuthenticationService authService) {
        return Optional.empty();
    }

    @Override
    public String getSaslInitHost(AddressSpace addressSpace, AuthenticationService authService) {
        return Optional.ofNullable(addressSpace.getAnnotation(AnnotationKeys.REALM_NAME)).orElse(addressSpace.getMetadata().getName());
    }

    @Override
    public Optional<String> getOAuthURL(AuthenticationService authService) {
        return Optional.empty();
    }
}
