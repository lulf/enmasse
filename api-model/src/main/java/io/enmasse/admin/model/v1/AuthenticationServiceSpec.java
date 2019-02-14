/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.admin.model.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.fabric8.kubernetes.api.model.Doneable;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import io.sundr.builder.annotations.Inline;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Buildable(
        editableEnabled = false,
        generateBuilderPackage = false,
        builderPackage = "io.fabric8.kubernetes.api.builder",
        refs= {
                @BuildableReference(AbstractWithAdditionalProperties.class)
        },
        inline = @Inline(type = Doneable.class, prefix = "Doneable", value = "done")
)
@JsonPropertyOrder({"version", "admin", "broker"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationServiceSpec extends AbstractWithAdditionalProperties {

    @NotNull @Valid
    private String host;

    @NotNull @Valid
    private int port;

    private String realm;
    private String caSecretName;
    private String clientSecretName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationServiceSpec that = (AuthenticationServiceSpec) o;
        return Objects.equals(host, that.host) &&
                Objects.equals(port, that.port) &&
                Objects.equals(realm, that.realm) &&
                Objects.equals(caSecretName, that.caSecretName) &&
                Objects.equals(clientSecretName, that.clientSecretName);
    }

    @Override
    public String toString() {
        return "AuthenticationServiceSpec{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", realm=" + realm +
                ", caSecretName=" + caSecretName +
                ", clientSecretName=" + clientSecretName +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, realm, caSecretName, clientSecretName);
    }
        public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getCaSecretName() {
        return caSecretName;
    }

    public void setCaSecretName(String caSecretName) {
        this.caSecretName = caSecretName;
    }

    public String getClientSecretName() {
        return clientSecretName;
    }

    public void setClientSecretName(String clientSecretName) {
        this.clientSecretName = clientSecretName;
    }

}
