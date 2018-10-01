/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardInfraConfigSpecBrokerResources {
    private final String memory;
    private final String storage;

    public StandardInfraConfigSpecBrokerResources(@JsonProperty("memory") String memory,
                                                    @JsonProperty("storage") String storage) {
        this.memory = memory;
        this.storage = storage;
    }

    public String getMemory() {
        return memory;
    }

    public String getStorage() {
        return storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardInfraConfigSpecBrokerResources that = (StandardInfraConfigSpecBrokerResources) o;
        return Objects.equals(memory, that.memory) &&
                Objects.equals(storage, that.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memory, storage);
    }
}
