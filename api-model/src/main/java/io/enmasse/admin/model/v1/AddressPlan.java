/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */

package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.enmasse.common.model.AbstractHasMetadata;
import io.enmasse.common.model.DefaultCustomResource;
import io.fabric8.kubernetes.api.model.Doneable;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import io.sundr.builder.annotations.Inline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Buildable(
        editableEnabled = false,
        generateBuilderPackage = false,
        builderPackage = "io.fabric8.kubernetes.api.builder",
        refs= {@BuildableReference(AbstractHasMetadataWithAdditionalProperties.class)},
        inline = @Inline(type = Doneable.class, prefix = "Doneable", value = "done")
)
@DefaultCustomResource
@SuppressWarnings("serial")
public class AddressPlan extends AbstractHasMetadataWithAdditionalProperties<AddressPlan> implements io.enmasse.admin.model.AddressPlan {

    public static final String KIND = "AddressPlan";

    private AddressPlanSpec spec;

    // Deprecated format
    private String shortDescription;
    private String addressType;
    private List<ResourceRequest> requiredResources;

    public AddressPlan() {
        super(KIND, AdminCrd.API_VERSION_V1BETA2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressPlan that = (AddressPlan) o;
        return Objects.equals(getMetadata(), that.getMetadata()) &&
                Objects.equals(spec, that.spec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMetadata(), spec);
    }

    @Override
    public String toString() {
        return "AddressPlan{" +
                "metadata='" + getMetadata() + '\'' +
                ", spec ='" + spec + '\'' +
                '}';
    }

    public void setSpec(AddressPlanSpec spec) {
        this.spec = spec;
    }

    public AddressPlanSpec getSpec() {
        return spec;
    }

    @Override
    public String getShortDescription() {
        if (spec != null) {
            return spec.getShortDescription();
        } else {
            return shortDescription;
        }
    }

    @Override
    public String getAddressType() {
        if (spec != null) {
            return spec.getAddressType();
        } else {
            return addressType;
        }
    }

    @Override
    public Map<String, Double> getResources() {
        if (spec != null) {
            return spec.getResources();
        } else {
            Map<String, Double> returnedResources = new HashMap<>();
            for (ResourceRequest resourceRequest : getRequiredResources()) {
                returnedResources.put(resourceRequest.getName(), resourceRequest.getCredit());
            }
            return returnedResources;
        }
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    List<ResourceRequest> getRequiredResources() {
        return requiredResources;
    }

    public void setRequiredResources(List<ResourceRequest> requiredResources) {
        this.requiredResources = requiredResources;
    }
}
