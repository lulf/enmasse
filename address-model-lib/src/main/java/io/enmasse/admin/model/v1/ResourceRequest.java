/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceRequest {
    private final String name;
    private final double credit;

    @JsonCreator
    public ResourceRequest(@JsonProperty("name") String name,
                           @JsonProperty("credit") double credit) {
        this.name = name;
        this.credit = credit;
    }

    public String getResourceName() {
        return name;
    }

    public double getAmount() {
        return credit;
    }
}
