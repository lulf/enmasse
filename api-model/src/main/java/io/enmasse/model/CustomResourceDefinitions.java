/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */

package io.enmasse.model;

import io.enmasse.admin.model.v1.AdminCrd;
import io.enmasse.user.model.v1.UserCrd;

public final class CustomResourceDefinitions {

    private CustomResourceDefinitions() {}

    /**
     * Register all custom resource definitions used by EnMasse.
     */
    public static void registerAll() {
        AdminCrd.registerCustomCrds();
        UserCrd.registerCustomCrds();
    }
}
