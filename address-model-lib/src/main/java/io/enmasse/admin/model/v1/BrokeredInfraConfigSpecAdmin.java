/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrokeredInfraConfigSpecAdmin {
    private final BrokeredInfraConfigSpecAdminResources resources;

    @JsonCreator
    public BrokeredInfraConfigSpecAdmin(@JsonProperty("resources") BrokeredInfraConfigSpecAdminResources resources) {
        this.resources = resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrokeredInfraConfigSpecAdmin that = (BrokeredInfraConfigSpecAdmin) o;
        return Objects.equals(resources, that.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resources);
    }

    public BrokeredInfraConfigSpecAdminResources getResources() {
        return resources;
    }
}
