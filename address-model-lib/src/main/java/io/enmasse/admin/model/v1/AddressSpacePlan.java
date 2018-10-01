/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.charset.StandardCharsets;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressSpacePlan {
    @JsonProperty("apiVersion")
    private final String apiVersion = "admin.enmasse.io/v1alpha1";
    @JsonProperty("kind")
    private final String kind = "AddressSpacePlan";

    private final ObjectMetadata metadata;

    private final String displayName;
    private final int displayOrder;
    private final String shortDescription;
    private final String longDescription;
    private final String uuid;
    private final String addressSpaceType;
    private final List<ResourceAllowance> resources;
    private final List<String> addressPlans;

    @JsonCreator
    private AddressSpacePlan(@JsonProperty("metadata") ObjectMetadata metadata,
                             @JsonProperty("displayName") String displayName,
                             @JsonProperty("displayOrder") int displayOrder,
                             @JsonProperty("shortDescription") String shortDescription,
                             @JsonProperty("longDescription") String longDescription,
                             @JsonProperty("uuid") String uuid,
                             @JsonProperty("addressSpaceType") String addressSpaceType,
                             @JsonProperty("resources") List<ResourceAllowance> resources,
                             @JsonProperty("addressPlans") List<String> addressPlans) {
        if (displayName == null) {
            displayName = metadata.getName();
        }
        if (shortDescription == null) {
            shortDescription = displayName;
        }
        if (longDescription == null) {
            longDescription = shortDescription;
        }
        if (uuid == null) {
            uuid = UUID.nameUUIDFromBytes(metadata.getName().getBytes(StandardCharsets.UTF_8)).toString();
        }
        this.metadata = metadata;
        this.displayName = displayName;
        this.displayOrder = displayOrder;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.uuid = uuid;
        this.addressSpaceType = addressSpaceType;
        this.resources = resources;
        this.addressPlans = addressPlans;
    }


    public List<ResourceAllowance> getResources() {
        return Collections.unmodifiableList(resources);
    }

    public List<String> getAddressPlans() {
        return Collections.unmodifiableList(addressPlans);
    }

    public ObjectMetadata getMetadata() {
        return metadata;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAddressSpaceType() {
        return addressSpaceType;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getUuid() {
        return uuid;
    }

    public void validate() {
        Objects.requireNonNull(displayName);
        Objects.requireNonNull(shortDescription);
        Objects.requireNonNull(longDescription);
        Objects.requireNonNull(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressSpacePlan that = (AddressSpacePlan) o;

        if (!metadata.equals(that.metadata)) return false;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        int result = metadata.hashCode();
        result = 31 * result + uuid.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AddressSpacePlan{" +
                "metadata='" + metadata+ '\'' +
                ", uuid='" + uuid + '\'' +
                ", resources=" + resources +
                ", addressPlans=" + addressPlans +
                '}';
    }

    public static class Builder {
        private ObjectMetadata metadata;
        private String displayName;
        private int displayOrder;
        private String shortDescription;
        private String longDescription;
        private String uuid;
        private String addressSpaceType;
        private List<ResourceAllowance> allowedResources;
        private List<String> addressPlans;

        public Builder setMetadata(ObjectMetadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDisplayOrder(int displayOrder) {
            this.displayOrder = displayOrder;
            return this;
        }

        public Builder setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder setLongDescription(String longDescription) {
            this.longDescription = longDescription;
            return this;
        }

        public Builder setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setAddressSpaceType(String addressSpaceType) {
            this.addressSpaceType = addressSpaceType;
            return this;
        }

        public Builder setResources(List<ResourceAllowance> allowedResources) {
            this.allowedResources = allowedResources;
            return this;
        }

        public Builder setAddressPlans(List<String> addressPlans) {
            this.addressPlans = addressPlans;
            return this;
        }

        public AddressSpacePlan build() {
            Objects.requireNonNull(metadata);
            Objects.requireNonNull(addressSpaceType);
            Objects.requireNonNull(allowedResources);
            Objects.requireNonNull(addressPlans);

            return new AddressSpacePlan(metadata, displayName, displayOrder, shortDescription, longDescription, uuid, addressSpaceType, allowedResources, addressPlans);
        }
    }
}
