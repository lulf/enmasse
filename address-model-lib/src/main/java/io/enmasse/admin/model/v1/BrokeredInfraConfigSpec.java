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
public class BrokeredInfraConfigSpec {

    private final String version;
    private final BrokeredInfraConfigSpecAdmin admin;
    private final BrokeredInfraConfigSpecBroker broker;

    @JsonCreator
    private BrokeredInfraConfigSpec(@JsonProperty("version") String version,
                                  @JsonProperty("admin") BrokeredInfraConfigSpecAdmin admin,
                                  @JsonProperty("broker") BrokeredInfraConfigSpecBroker broker) {
        this.version = version;
        this.admin = admin;
        this.broker = broker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrokeredInfraConfigSpec that = (BrokeredInfraConfigSpec) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(admin, that.admin) &&
                Objects.equals(broker, that.broker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, admin, broker);
    }

    public BrokeredInfraConfigSpecAdmin getAdmin() {
        return admin;
    }

    public BrokeredInfraConfigSpecBroker getBroker() {
        return broker;
    }

    public String getVersion() {
        return version;
    }
}
