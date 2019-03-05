/*
 * Copyright 2019, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.enmasse.common.model.AbstractList;
import io.enmasse.common.model.DefaultCustomResource;

@DefaultCustomResource
@SuppressWarnings("serial")
public class AuthenticationServiceList extends AbstractList<AuthenticationService> {

    public static final String KIND = "AuthenticationServiceList";

    @JsonCreator
    public AuthenticationServiceList() {
        super(KIND, AdminCrd.API_VERSION_V1ALPHA1);
    }
}
