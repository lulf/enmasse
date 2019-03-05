/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.controller;

import io.enmasse.address.model.*;
import io.enmasse.admin.model.v1.AuthenticationService;
import io.enmasse.admin.model.v1.BrokeredInfraConfig;
import io.enmasse.admin.model.v1.InfraConfig;
import io.enmasse.admin.model.v1.StandardInfraConfig;
import io.enmasse.config.AnnotationKeys;
import io.enmasse.config.LabelKeys;
import io.enmasse.controller.common.Kubernetes;
import io.enmasse.controller.common.TemplateParameter;
import io.enmasse.k8s.api.AuthenticationServiceRegistry;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.api.model.apps.StatefulSet;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.*;

public class TemplateInfraResourceFactory implements InfraResourceFactory {
    private static final String KC_IDP_HINT_NONE = "none";
    private static final String KC_IDP_HINT_OPENSHIFT = "openshift-v3";

    private final Kubernetes kubernetes;
    private final AuthenticationServiceRegistry authenticationServiceRegistry;
    private final boolean openShift;

    public TemplateInfraResourceFactory(Kubernetes kubernetes, AuthenticationServiceRegistry authenticationServiceRegistry, boolean openShift) {
        this.kubernetes = kubernetes;
        this.authenticationServiceRegistry = authenticationServiceRegistry;
        this.openShift = openShift;
    }

    private void prepareParameters(InfraConfig infraConfig,
                                   AddressSpace addressSpace,
                                   Map<String, String> parameters) {

        AuthenticationService authService = authenticationServiceRegistry.findAuthenticationService(addressSpace.getSpec().getAuthenticationService())
                .orElseThrow(() -> new IllegalArgumentException("Unable to find authentication service " + addressSpace.getSpec().getAuthenticationService()));


        Optional<String> kcIdpHint = getKcIdpHint(infraConfig, addressSpace, authService);

        String infraUuid = addressSpace.getAnnotation(AnnotationKeys.INFRA_UUID);
        parameters.put(TemplateParameter.INFRA_NAMESPACE, kubernetes.getNamespace());
        parameters.put(TemplateParameter.ADDRESS_SPACE, addressSpace.getMetadata().getName());
        parameters.put(TemplateParameter.INFRA_UUID, infraUuid);
        parameters.put(TemplateParameter.ADDRESS_SPACE_NAMESPACE, addressSpace.getMetadata().getNamespace());
        parameters.put(TemplateParameter.AUTHENTICATION_SERVICE_HOST, authService.getSpec().getHost());
        parameters.put(TemplateParameter.AUTHENTICATION_SERVICE_PORT, String.valueOf(authService.getSpec().getPort()));
        parameters.put(TemplateParameter.ADDRESS_SPACE_PLAN, addressSpace.getSpec().getPlan());
        parameters.put(TemplateParameter.AUTHENTICATION_SERVICE_KC_IDP_HINT, kcIdpHint.orElse(""));

        String encodedCaCert = Optional.ofNullable(authService.getSpec().getCaCertSecretName())
                .map(secretName ->
                    kubernetes.getSecret(secretName).map(secret ->
                            secret.getData().get("tls.crt"))
                            .orElseThrow(() -> new IllegalArgumentException("Unable to decode secret " + secretName)))
                .orElseGet(() -> {
                    try {
                        return Base64.getEncoder().encodeToString(Files.readAllBytes(new File("/etc/ssl/certs/ca-bundle.crt").toPath()));
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
        parameters.put(TemplateParameter.AUTHENTICATION_SERVICE_CA_CERT, encodedCaCert);
        if (authService.getSpec().getClientCertSecretName() != null) {
            parameters.put(TemplateParameter.AUTHENTICATION_SERVICE_CLIENT_SECRET, authService.getSpec().getClientCertSecretName());
        }

        if (authService.getSpec().getRealm() != null) {
            parameters.put(TemplateParameter.AUTHENTICATION_SERVICE_SASL_INIT_HOST, authService.getSpec().getRealm());
        } else {
            parameters.put(TemplateParameter.AUTHENTICATION_SERVICE_SASL_INIT_HOST, addressSpace.getAnnotation(AnnotationKeys.REALM_NAME));
        }

        if (authService.getMetadata().getAnnotations() != null &&
                authService.getMetadata().getAnnotations().get(AnnotationKeys.OAUTH_URL) != null) {
            parameters.put(TemplateParameter.AUTHENTICATION_SERVICE_OAUTH_URL, authService.getMetadata().getAnnotations().get(AnnotationKeys.OAUTH_URL));
        }

        Map<String, CertSpec> serviceCertMapping = new HashMap<>();
        for (EndpointSpec endpoint : addressSpace.getSpec().getEndpoints()) {
            if (endpoint.getCert() != null) {
                serviceCertMapping.put(endpoint.getService(), endpoint.getCert());
            }
        }
        parameters.put(TemplateParameter.MESSAGING_SECRET, serviceCertMapping.get("messaging").getSecretName());
        parameters.put(TemplateParameter.CONSOLE_SECRET, serviceCertMapping.get("console").getSecretName());
    }

    private Optional<String> getKcIdpHint(final InfraConfig infraConfig,
                                final AddressSpace addressSpace,
                                final AuthenticationService authenticationService) {

        String kcIdpHint = null;
        if (this.openShift && authenticationService.getMetadata().getAnnotations() != null &&
                "standard".equals(authenticationService.getMetadata().getName())) {
            kcIdpHint = KC_IDP_HINT_OPENSHIFT;
        }

        kcIdpHint = getAnnotation(infraConfig.getMetadata().getAnnotations(), AnnotationKeys.KC_IDP_HINT, kcIdpHint);

        final String hint = addressSpace.getAnnotation(AnnotationKeys.KC_IDP_HINT);
        if  ( hint != null) {
            kcIdpHint = hint;
        }
        return KC_IDP_HINT_NONE.equals(kcIdpHint) ? Optional.empty() : Optional.ofNullable(kcIdpHint);
    }

    private void prepareMqttParameters(AddressSpace addressSpace, Map<String, String> parameters) {
        String infraUuid = addressSpace.getAnnotation(AnnotationKeys.INFRA_UUID);
        parameters.put(TemplateParameter.ADDRESS_SPACE, addressSpace.getMetadata().getName());
        parameters.put(TemplateParameter.INFRA_UUID, infraUuid);
        Map<String, CertSpec> serviceCertMapping = new HashMap<>();
        for (EndpointSpec endpoint : addressSpace.getSpec().getEndpoints()) {
            if (endpoint.getCert() != null) {
                serviceCertMapping.put(endpoint.getService(), endpoint.getCert());
            }
        }
        parameters.put(TemplateParameter.MQTT_SECRET, serviceCertMapping.get("mqtt").getSecretName());
    }

    private List<HasMetadata> createStandardInfraMqtt(AddressSpace addressSpace, String templateName) {
        Map<String, String> parameters = new HashMap<>();
        prepareMqttParameters(addressSpace, parameters);
        return new ArrayList<>(kubernetes.processTemplate(templateName, parameters).getItems());
    }


    private List<HasMetadata> createStandardInfra(AddressSpace addressSpace, StandardInfraConfig standardInfraConfig) {

        Map<String, String> parameters = new HashMap<>();

        prepareParameters(standardInfraConfig, addressSpace, parameters);

        if (standardInfraConfig.getSpec().getBroker() != null) {
            if (standardInfraConfig.getSpec().getBroker().getResources() != null) {
                if (standardInfraConfig.getSpec().getBroker().getResources().getMemory() != null) {
                    parameters.put(TemplateParameter.BROKER_MEMORY_LIMIT, standardInfraConfig.getSpec().getBroker().getResources().getMemory());
                }
                if (standardInfraConfig.getSpec().getBroker().getResources().getStorage() != null) {
                    parameters.put(TemplateParameter.BROKER_STORAGE_CAPACITY, standardInfraConfig.getSpec().getBroker().getResources().getStorage());
                }
            }

            if (standardInfraConfig.getSpec().getBroker().getAddressFullPolicy() != null) {
                parameters.put(TemplateParameter.BROKER_ADDRESS_FULL_POLICY, standardInfraConfig.getSpec().getBroker().getAddressFullPolicy());
            }

            if (standardInfraConfig.getSpec().getBroker().getGlobalMaxSize() != null) {
                parameters.put(TemplateParameter.BROKER_GLOBAL_MAX_SIZE, standardInfraConfig.getSpec().getBroker().getGlobalMaxSize());
            }
        }

        if (standardInfraConfig.getSpec().getRouter() != null) {
            if (standardInfraConfig.getSpec().getRouter().getResources() != null && standardInfraConfig.getSpec().getRouter().getResources().getMemory() != null) {
                parameters.put(TemplateParameter.ROUTER_MEMORY_LIMIT, standardInfraConfig.getSpec().getRouter().getResources().getMemory());
            }

            if (standardInfraConfig.getSpec().getRouter().getLinkCapacity() != null) {
                parameters.put(TemplateParameter.ROUTER_LINK_CAPACITY, String.valueOf(standardInfraConfig.getSpec().getRouter().getLinkCapacity()));
            }

            if (standardInfraConfig.getSpec().getRouter().getHandshakeTimeout() != null) {
                parameters.put(TemplateParameter.ROUTER_HANDSHAKE_TIMEOUT, String.valueOf(standardInfraConfig.getSpec().getRouter().getHandshakeTimeout()));
            }

        }

        if (standardInfraConfig.getSpec().getAdmin() != null && standardInfraConfig.getSpec().getAdmin().getResources() != null && standardInfraConfig.getSpec().getAdmin().getResources().getMemory() != null) {
            parameters.put(TemplateParameter.ADMIN_MEMORY_LIMIT, standardInfraConfig.getSpec().getAdmin().getResources().getMemory());
        }

        parameters.put(TemplateParameter.STANDARD_INFRA_CONFIG_NAME, standardInfraConfig.getMetadata().getName());

        Map<String, String> infraAnnotations = standardInfraConfig.getMetadata().getAnnotations();
        String templateName = getAnnotation(infraAnnotations, AnnotationKeys.TEMPLATE_NAME, "standard-space-infra");
        List<HasMetadata> items = new ArrayList<>(kubernetes.processTemplate(templateName, parameters).getItems());

        if (standardInfraConfig.getSpec().getRouter() != null && standardInfraConfig.getSpec().getRouter().getMinReplicas() != null) {
            // Workaround since parameterized integer fields cannot be loaded locally by fabric8 kubernetes-client
            for (HasMetadata item : items) {
                if (item instanceof StatefulSet && "qdrouterd".equals(item.getMetadata().getLabels().get(LabelKeys.NAME))) {
                    StatefulSet router = (StatefulSet) item;
                    router.getSpec().setReplicas(standardInfraConfig.getSpec().getRouter().getMinReplicas());
                }
            }
        }

        if (Boolean.parseBoolean(getAnnotation(infraAnnotations, AnnotationKeys.WITH_MQTT, "false"))) {
            String mqttTemplateName = getAnnotation(infraAnnotations, AnnotationKeys.MQTT_TEMPLATE_NAME, "standard-space-infra-mqtt");
            items.addAll(createStandardInfraMqtt(addressSpace, mqttTemplateName));
        }

        if (standardInfraConfig.getSpec().getBroker() != null) {
            return applyStorageClassName(standardInfraConfig.getSpec().getBroker().getStorageClassName(), items);
        } else {
            return items;
        }
    }


    private String getAnnotation(Map<String, String> annotations, String key, String defaultValue) {
        return Optional.ofNullable(annotations)
                .flatMap(m -> Optional.ofNullable(m.get(key)))
                .orElse(defaultValue);
    }

    private List<HasMetadata> createBrokeredInfra(AddressSpace addressSpace, BrokeredInfraConfig brokeredInfraConfig) {
        Map<String, String> parameters = new HashMap<>();

        prepareParameters(brokeredInfraConfig, addressSpace, parameters);

        if (brokeredInfraConfig.getSpec().getBroker() != null) {
            if (brokeredInfraConfig.getSpec().getBroker().getResources() != null) {
                if (brokeredInfraConfig.getSpec().getBroker().getResources().getMemory() != null) {
                    parameters.put(TemplateParameter.BROKER_MEMORY_LIMIT, brokeredInfraConfig.getSpec().getBroker().getResources().getMemory());
                }
                if (brokeredInfraConfig.getSpec().getBroker().getResources().getStorage() != null) {
                    parameters.put(TemplateParameter.BROKER_STORAGE_CAPACITY, brokeredInfraConfig.getSpec().getBroker().getResources().getStorage());
                }
            }

            if (brokeredInfraConfig.getSpec().getBroker().getAddressFullPolicy() != null) {
                parameters.put(TemplateParameter.BROKER_ADDRESS_FULL_POLICY, brokeredInfraConfig.getSpec().getBroker().getAddressFullPolicy());
            }

            if (brokeredInfraConfig.getSpec().getBroker().getGlobalMaxSize() != null) {
                parameters.put(TemplateParameter.BROKER_GLOBAL_MAX_SIZE, brokeredInfraConfig.getSpec().getBroker().getGlobalMaxSize());
            }
        }

        if (brokeredInfraConfig.getSpec().getAdmin() != null && brokeredInfraConfig.getSpec().getAdmin().getResources() != null && brokeredInfraConfig.getSpec().getAdmin().getResources().getMemory() != null) {
            parameters.put(TemplateParameter.ADMIN_MEMORY_LIMIT, brokeredInfraConfig.getSpec().getAdmin().getResources().getMemory());
        }

        String templateName = getAnnotation(brokeredInfraConfig.getMetadata().getAnnotations(), AnnotationKeys.TEMPLATE_NAME, "brokered-space-infra");
        if (brokeredInfraConfig.getSpec().getBroker() != null) {
            return applyStorageClassName(brokeredInfraConfig.getSpec().getBroker().getStorageClassName(), kubernetes.processTemplate(templateName, parameters).getItems());
        } else {
            return kubernetes.processTemplate(templateName, parameters).getItems();
        }
    }

    private List<HasMetadata> applyStorageClassName(String storageClassName, List<HasMetadata> items) {
        if (storageClassName != null) {
            for (HasMetadata item : items) {
                if (item instanceof PersistentVolumeClaim) {
                    ((PersistentVolumeClaim) item).getSpec().setStorageClassName(storageClassName);
                }
            }
        }
        return items;
    }

    @Override
    public List<HasMetadata> createInfraResources(AddressSpace addressSpace, InfraConfig infraConfig) {
        if ("standard".equals(addressSpace.getSpec().getType())) {
            return createStandardInfra(addressSpace, (StandardInfraConfig) infraConfig);
        } else if ("brokered".equals(addressSpace.getSpec().getType())) {
            return createBrokeredInfra(addressSpace, (BrokeredInfraConfig) infraConfig);
        } else {
            throw new IllegalArgumentException("Unknown address space type " + addressSpace.getSpec().getType());
        }
    }
}
