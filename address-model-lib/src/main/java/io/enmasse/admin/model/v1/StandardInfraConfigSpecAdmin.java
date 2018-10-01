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
public class StandardInfraConfigSpecAdmin {
    private final StandardInfraConfigSpecAdminResources resources;

    @JsonCreator
    public StandardInfraConfigSpecAdmin(@JsonProperty("resources") StandardInfraConfigSpecAdminResources resources) {
        this.resources = resources;
    }

    public StandardInfraConfigSpecAdminResources getResources() {
        return resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardInfraConfigSpecAdmin that = (StandardInfraConfigSpecAdmin) o;
        return Objects.equals(resources, that.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resources);
    }
}
