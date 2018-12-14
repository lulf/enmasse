/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.controller;

import io.enmasse.controller.auth.CertProvider;

public interface CertProviderFactory {
    CertProvider createProvider(String providerName);
    String getDefaultProviderName();
}
