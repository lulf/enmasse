/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.*;
import io.fabric8.kubernetes.api.model.Doneable;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.Inline;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Buildable(
        editableEnabled = false,
        generateBuilderPackage = false,
        builderPackage = "io.fabric8.kubernetes.api.builder",
        inline = @Inline(type = Doneable.class, prefix = "Doneable", value = "done")
)
@JsonPropertyOrder({"resources", "linkCapacity"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardInfraConfigSpecRouter {
    private StandardInfraConfigSpecRouterResources resources;
    private String linkCapacity;
    private Map<String, Object> additionalProperties = new HashMap<>(0);

    public StandardInfraConfigSpecRouter() { }

    public StandardInfraConfigSpecRouter(StandardInfraConfigSpecRouterResources resources, String linkCapacity) {
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void setResources(StandardInfraConfigSpecRouterResources resources) {
        this.resources = resources;
    }

    public void setLinkCapacity(String linkCapacity) {
        this.linkCapacity = linkCapacity;
    }
}
