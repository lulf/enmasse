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
public class StandardInfraConfigSpecRouter {
    private final StandardInfraConfigSpecRouterResources resources;
    private final String linkCapacity;

    @JsonCreator
    public StandardInfraConfigSpecRouter(@JsonProperty("resources") StandardInfraConfigSpecRouterResources resources,
                                           @JsonProperty("linkCapacity") String linkCapacity) {
        this.resources = resources;
        this.linkCapacity = linkCapacity;
    }

    public StandardInfraConfigSpecRouterResources getResources() {
        return resources;
    }

    public String getLinkCapacity() {
        return linkCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardInfraConfigSpecRouter that = (StandardInfraConfigSpecRouter) o;
        return Objects.equals(resources, that.resources) &&
                Objects.equals(linkCapacity, that.linkCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resources, linkCapacity);
    }
}
