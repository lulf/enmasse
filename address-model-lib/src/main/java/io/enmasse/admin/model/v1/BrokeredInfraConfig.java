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
public class BrokeredInfraConfig implements InfraConfig {
    @JsonProperty("apiVersion")
    private final String apiVersion = "admin.enmasse.io/v1alpha1";
    @JsonProperty("kind")
    private final String kind = "BrokeredInfraConfig";

    private final ObjectMetadata metadata;
    private final BrokeredInfraConfigSpec spec;

    @JsonCreator
    private BrokeredInfraConfig(@JsonProperty("metadata") ObjectMetadata metadata,
                                  @JsonProperty("spec") BrokeredInfraConfigSpec spec) {
        this.metadata = metadata;
        this.spec = spec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrokeredInfraConfig that = (BrokeredInfraConfig) o;
        return Objects.equals(metadata, that.metadata) &&
                Objects.equals(spec, that.spec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadata, spec);
    }


    @Override
    public String toString() {
        return "BrokeredInfraConfig{" +
                "metadata=" + metadata +
                ", spec=" + spec + "}";
    }

    public ObjectMetadata getMetadata() {
        return metadata;
    }

    public BrokeredInfraConfigSpec getSpec() {
        return spec;
    }

    public static class Builder {
        private ObjectMetadata metadata;
        private BrokeredInfraConfigSpec spec;

        public Builder setMetadata(ObjectMetadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder setSpec(BrokeredInfraConfigSpec spec) {
            this.spec = spec;
            return this;
        }

        public BrokeredInfraConfig build() {
            Objects.requireNonNull(metadata);
            Objects.requireNonNull(spec);

            return new BrokeredInfraConfig(metadata, spec);
        }
    }
}
