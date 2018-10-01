/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressPlan {
    @JsonProperty("apiVersion")
    private final String apiVersion = "admin.enmasse.io/v1alpha1";
    @JsonProperty("kind")
    private final String kind = "AddressPlan";

    private final ObjectMetadata metadata;

    private final String displayName;
    private final int displayOrder;
    private final String shortDescription;
    private final String longDescription;
    private final String uuid;
    private final String addressType;
    private final List<ResourceRequest> requiredResources;

    @JsonCreator
    private AddressPlan(@JsonProperty("metadata") ObjectMetadata metadata,
                        @JsonProperty("displayName") String displayName,
                        @JsonProperty("displayOrder") int displayOrder,
                        @JsonProperty("shortDescription") String shortDescription,
                        @JsonProperty("longDescription") String longDescription,
                        @JsonProperty("uuid") String uuid,
                        @JsonProperty("addressType") String addressType,
                        @JsonProperty("requiredResources") List<ResourceRequest> requiredResources) {
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
        this.addressType = addressType;
        this.requiredResources = requiredResources;
    }


    public List<ResourceRequest> getRequiredResources() {
        return Collections.unmodifiableList(requiredResources);
    }

    public ObjectMetadata getMetadata() {
        return metadata;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAddressType() {
        return addressType;
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

        AddressPlan that = (AddressPlan) o;

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
        return "AddressPlan{" +
                "metadata='" + metadata+ '\'' +
                ", uuid='" + uuid + '\'' +
                ", requiredResources=" + requiredResources +
                '}';
    }

    public static class Builder {
        private ObjectMetadata metadata;
        private String displayName;
        private int displayOrder;
        private String shortDescription;
        private String longDescription;
        private String uuid;
        private String addressType;
        private List<ResourceRequest> requiredResources;

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

        public Builder setAddressType(String addressType) {
            this.addressType = addressType;
            return this;
        }

        public Builder setRequiredResources(List<ResourceRequest> requiredResources) {
            this.requiredResources = requiredResources;
            return this;
        }

        public AddressPlan build() {
            Objects.requireNonNull(metadata);

            Objects.requireNonNull(addressType);
            Objects.requireNonNull(requiredResources);

            return new AddressPlan(metadata, displayName, displayOrder, shortDescription, longDescription, uuid, addressType, requiredResources);
        }
    }
}
