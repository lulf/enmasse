/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardInfraConfigSpecRouterResources {
    private final String memory;

    public StandardInfraConfigSpecRouterResources(@JsonProperty("memory") String memory) {
        this.memory = memory;
    }

    public String getMemory() {
        return memory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardInfraConfigSpecRouterResources that = (StandardInfraConfigSpecRouterResources) o;
        return Objects.equals(memory, that.memory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memory);
    }
}
