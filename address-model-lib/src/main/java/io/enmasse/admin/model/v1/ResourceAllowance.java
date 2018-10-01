/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceAllowance {
    private final String name;
    private final double min;
    private final double max;

    @JsonCreator
    public ResourceAllowance(@JsonProperty("name") String name,
                             @JsonProperty("min") double min,
                             @JsonProperty("max") double max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
