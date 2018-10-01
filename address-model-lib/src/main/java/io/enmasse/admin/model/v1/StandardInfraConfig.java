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
public class StandardInfraConfig implements InfraConfig {
    @JsonProperty("apiVersion")
    private final String apiVersion = "admin.enmasse.io/v1alpha1";
    @JsonProperty("kind")
    private final String kind = "StandardInfraConfig";

    private final ObjectMetadata metadata;
    private final StandardInfraConfigSpec spec;

    @JsonCreator
    public StandardInfraConfig(@JsonProperty("metadata") ObjectMetadata metadata,
                               @JsonProperty("spec") StandardInfraConfigSpec spec) {
        this.metadata = metadata;
        this.spec = spec;
    }

    public ObjectMetadata getMetadata() {
        return metadata;
    }

    public StandardInfraConfigSpec getSpec() {
        return spec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardInfraConfig that = (StandardInfraConfig) o;
        return Objects.equals(metadata, that.metadata) &&
                Objects.equals(spec, that.spec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadata, spec);
    }

    @Override
    public String toString() {
        return "StandardInfraConfig{" +
                "metadata=" + metadata +
                ", spec=" + spec + "}";
    }

    public static class Builder {
        private ObjectMetadata metadata;
        private StandardInfraConfigSpec spec;

        public Builder setMetadata(ObjectMetadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder setSpec(StandardInfraConfigSpec spec) {
            this.spec = spec;
            return this;
        }

        public StandardInfraConfig build() {
            Objects.requireNonNull(metadata);
            Objects.requireNonNull(spec);

            return new StandardInfraConfig(metadata, spec);
        }
    }
}
