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
public class StandardInfraConfigSpecBroker {
    private final StandardInfraConfigSpecBrokerResources resources;
    private final String addressFullPolicy;

    @JsonCreator
    public StandardInfraConfigSpecBroker(@JsonProperty("resources") StandardInfraConfigSpecBrokerResources resources,
                                           @JsonProperty("addressFullPolicy") String addressFullPolicy) {
        this.resources = resources;
        this.addressFullPolicy = addressFullPolicy;
    }

    public StandardInfraConfigSpecBrokerResources getResources() {
        return resources;
    }

    public String getAddressFullPolicy() {
        return addressFullPolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardInfraConfigSpecBroker that = (StandardInfraConfigSpecBroker) o;
        return Objects.equals(resources, that.resources) &&
                Objects.equals(addressFullPolicy, that.addressFullPolicy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resources, addressFullPolicy);
    }
}
