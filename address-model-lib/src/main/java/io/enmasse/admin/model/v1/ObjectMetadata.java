/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObjectMetadata {
    private final String name;
    private final String namespace;
    private final String creationTimestamp;
    private final String selfLink;
    private final String resourceVersion;
    private final Map<String, String> labels;
    private final Map<String, String> annotations;

    @JsonCreator
    public ObjectMetadata(@JsonProperty("name") String name,
                          @JsonProperty("namespace") String namespace,
                          @JsonProperty("creationTimestamp") String creationTimestamp,
                          @JsonProperty("selfLink") String selfLink,
                          @JsonProperty("resourceVersion") String resourceVersion,
                          @JsonProperty("labels") Map<String, String> labels,
                          @JsonProperty("annotations") Map<String, String> annotations) {
        this.name = name;
        this.namespace = namespace;
        this.creationTimestamp = creationTimestamp;
        this.selfLink = selfLink;
        this.resourceVersion = resourceVersion;
        this.labels = labels;
        this.annotations = annotations;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public String getSelfLink() {
        return selfLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectMetadata that = (ObjectMetadata) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(namespace, that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, namespace);
    }

    @Override
    public String toString() {
        return "ObjectMetadata{" +
                "name=" + name +
                ", namespace=" + namespace +
                ", annotations=" + annotations +
                ", labels=" + labels + "}";
    }

    public void validate() {
        Objects.requireNonNull(name, "'name' must be set");
        Objects.requireNonNull(namespace, "'namespace' must be set");
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public Map<String, String> getAnnotations() {
        return annotations;
    }

    public static class Builder {
        private String name;
        private String namespace;
        private String creationTimestamp;
        private String selfLink;
        private String resourceVersion;
        private Map<String, String> labels;
        private Map<String, String> annotations;

        public Builder() { }

        public Builder(ObjectMetadata otherMetadata) {
            this.name = otherMetadata.getName();
            this.namespace = otherMetadata.getNamespace();
            this.creationTimestamp = otherMetadata.getCreationTimestamp();
            this.selfLink = otherMetadata.getSelfLink();
            this.resourceVersion = otherMetadata.getResourceVersion();
            this.labels = otherMetadata.getLabels() != null ? new HashMap<>(otherMetadata.getLabels()) : null;
            this.annotations = otherMetadata.getAnnotations() != null ? new HashMap<>(otherMetadata.getAnnotations()) : null;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setNamespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder setCreationTimestamp(String creationTimestamp) {
            this.creationTimestamp = creationTimestamp;
            return this;
        }

        public Builder setSelfLink(String selfLink) {
            this.selfLink = selfLink;
            return this;
        }

        public Builder setResourceVersion(String resourceVersion) {
            this.resourceVersion = resourceVersion;
            return this;
        }

        public Builder setAnnotations(Map<String, String> annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder setLabels(Map<String, String> labels) {
            this.labels = labels;
            return this;
        }

        public ObjectMetadata build() {
            Objects.requireNonNull(name);
            return new ObjectMetadata(name, namespace, creationTimestamp, selfLink, resourceVersion, labels, annotations);
        }
    }
}
