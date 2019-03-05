/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.controller.keycloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.enmasse.admin.model.v1.AuthenticationService;
import io.enmasse.config.AnnotationKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class KeycloakRealmParams {

    private static final Logger log = LoggerFactory.getLogger(KeycloakRealmParams.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    public static final KeycloakRealmParams NULL_PARAMS = new KeycloakRealmParams(null, null, null, null);

    private final String identityProviderUrl;
    private final String identityProviderClientId;
    private final String identityProviderClientSecret;
    private final Map<String, String> browserSecurityHeaders;

    public KeycloakRealmParams(String identityProviderUrl, String identityProviderClientId, String identityProviderClientSecret, Map<String, String> browserSecurityHeaders) {
        this.identityProviderUrl = identityProviderUrl;
        this.identityProviderClientId = identityProviderClientId;
        this.identityProviderClientSecret = identityProviderClientSecret;
        this.browserSecurityHeaders = browserSecurityHeaders == null ? Collections.emptyMap() : new HashMap<>(browserSecurityHeaders);
    }

    public static KeycloakRealmParams fromAuthenticationService(AuthenticationService authenticationService) {

        String identityProviderUrl = authenticationService.getAnnotation(AnnotationKeys.IDENTITY_PROVIDER_URL);
        String identityProviderClientId = authenticationService.getAnnotation(AnnotationKeys.IDENTITY_PROVIDER_CLIENT_ID);
        String identityProviderClientSecret = authenticationService.getAnnotation(AnnotationKeys.IDENTITY_PROVIDER_CLIENT_SECRET);

        Map<String, String> browserSecurityHeaders = new HashMap<>();
        String browserSecurityHeadersString = authenticationService.getAnnotation(AnnotationKeys.BROWSER_SECURITY_HEADERS);
        if (browserSecurityHeadersString != null) {
            try {
                ObjectNode data = mapper.readValue(browserSecurityHeadersString, ObjectNode.class);
                Iterator<String> it = data.fieldNames();
                while (it.hasNext()) {
                    String key = it.next();
                    browserSecurityHeaders.put(key, data.get(key).asText());
                }
            } catch (IOException e) {
                log.warn("Error parsing browserSecurityHeaders, skipping", e);
            }
        }

        return new KeycloakRealmParams(identityProviderUrl, identityProviderClientId, identityProviderClientSecret, browserSecurityHeaders);
    }

    @Override
    public String toString() {
        return "{identityProviderUrl=" + identityProviderUrl + "," +
                "identityProviderClientId=" + identityProviderClientId + "," +
                "browserSecurityHeaders=" + browserSecurityHeaders + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeycloakRealmParams that = (KeycloakRealmParams) o;
        return Objects.equals(identityProviderUrl, that.identityProviderUrl) &&
                Objects.equals(identityProviderClientId, that.identityProviderClientId) &&
                Objects.equals(identityProviderClientSecret, that.identityProviderClientSecret) &&
                Objects.equals(browserSecurityHeaders, that.browserSecurityHeaders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identityProviderUrl, identityProviderClientId, identityProviderClientSecret, browserSecurityHeaders);
    }

    public String getIdentityProviderUrl() {
        return identityProviderUrl;
    }

    public String getIdentityProviderClientId() {
        return identityProviderClientId;
    }

    public String getIdentityProviderClientSecret() {
        return identityProviderClientSecret;
    }

    public Map<String, String> getBrowserSecurityHeaders() {
        return Collections.unmodifiableMap(browserSecurityHeaders);
    }
}
