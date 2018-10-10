/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.controller.standard;

import io.enmasse.admin.model.v1.AddressSpacePlan;
import io.enmasse.admin.model.v1.StandardInfraConfig;
import io.enmasse.k8s.api.*;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.NamespacedOpenShiftClient;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Clock;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;


/**
 * The standard controller is responsible for watching address spaces of type standard, creating
 * infrastructure required and propagating relevant status information.
 */

public class StandardController {
    private static final Logger log = LoggerFactory.getLogger(StandardController.class.getName());

    public static void main(String[] args) throws Exception {
        Map<String, String> env = System.getenv();

        TemplateOptions templateOptions = new TemplateOptions(env);
        File templateDir = new File(getEnvOrThrow(env, "TEMPLATE_DIR"));
        if (!templateDir.exists()) {
            throw new IllegalArgumentException("Template directory " + templateDir.getAbsolutePath() + " not found");
        }

        String certDir = getEnvOrThrow(env, "CERT_DIR");
        String addressSpace = getEnvOrThrow(env, "ADDRESS_SPACE");
        String addressSpacePlanName = getEnvOrThrow(env, "ADDRESS_SPACE_PLAN");
        String infraUuid = getEnvOrThrow(env, "INFRA_UUID");
        Duration resyncInterval = getEnv(env, "RESYNC_INTERVAL")
                .map(i -> Duration.ofSeconds(Long.parseLong(i)))
                .orElse(Duration.ofMinutes(5));

        Duration recheckInterval = getEnv(env, "CHECK_INTERVAL")
                .map(i -> Duration.ofSeconds(Long.parseLong(i)))
                .orElse(Duration.ofSeconds(30));

        NamespacedOpenShiftClient openShiftClient = new DefaultOpenShiftClient();

        CustomResourceDefinition addressSpacePlanDefinition = openShiftClient.customResourceDefinitions().withName(getEnvOrThrow(env, "ADDRESS_SPACE_PLAN_CRD_NAME")).get();
        AddressSpacePlanApi addressSpacePlanApi = new KubeAddressSpacePlanApi(openShiftClient, addressSpacePlanDefinition);

        CustomResourceDefinition addressPlanDefinition = openShiftClient.customResourceDefinitions().withName(getEnvOrThrow(env, "ADDRESS_PLAN_CRD_NAME")).get();
        AddressPlanApi addressPlanApi = new KubeAddressPlanApi(openShiftClient, addressPlanDefinition);

        CustomResourceDefinition brokeredInfraConfigDefinition = openShiftClient.customResourceDefinitions().withName(getEnvOrThrow(env, "BROKERED_INFRA_CONFIG_CRD_NAME")).get();
        BrokeredInfraConfigApi brokeredInfraConfigApi = new KubeBrokeredInfraConfigApi(openShiftClient, brokeredInfraConfigDefinition);

        CustomResourceDefinition standardInfraConfigDefinition = openShiftClient.customResourceDefinitions().withName(getEnvOrThrow(env, "STANDARD_INFRA_CONFIG_CRD_NAME")).get();
        StandardInfraConfigApi standardInfraConfigApi = new KubeStandardInfraConfigApi(openShiftClient, standardInfraConfigDefinition);

        SchemaApi schemaApi = new KubeSchemaApi(addressSpacePlanApi, addressPlanApi, brokeredInfraConfigApi, standardInfraConfigApi);
        CachingSchemaProvider schemaProvider = new CachingSchemaProvider();
        schemaApi.watchSchema(schemaProvider, resyncInterval);

        Kubernetes kubernetes = new KubernetesHelper(openShiftClient, templateDir, infraUuid);
        BrokerSetGenerator clusterGenerator = new TemplateBrokerSetGenerator(kubernetes, templateOptions, addressSpace, infraUuid, schemaProvider);

        boolean enableEventLogger = Boolean.parseBoolean(getEnv(env, "ENABLE_EVENT_LOGGER").orElse("false"));
        EventLogger eventLogger = enableEventLogger ? new KubeEventLogger(openShiftClient, openShiftClient.getNamespace(), Clock.systemUTC(), "standard-controller")
                : new LogEventLogger();


        AddressController addressController = new AddressController(
                addressSpace,
                addressSpacePlanName,
                infraUuid,
                new ConfigMapAddressApi(openShiftClient, openShiftClient.getNamespace(), infraUuid),
                kubernetes,
                clusterGenerator,
                certDir,
                eventLogger,
                schemaProvider,
                recheckInterval,
                resyncInterval
        );

        log.info("Deploying address space controller for " + addressSpace);
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(addressController, result -> {
            if (result.succeeded()) {
                log.info("Standard controller for {} deployed", addressSpace);
            } else {
                log.warn("Unable to deploy standard controller for {}", addressSpace);
            }
        });
        vertx.createHttpServer()
                .requestHandler(request -> request.response().setStatusCode(200).end()).listen(8889);
    }

    private static Optional<String> getEnv(Map<String, String> env, String envVar) {
        return Optional.ofNullable(env.get(envVar));
    }

    private static String getEnvOrThrow(Map<String, String> env, String envVar) {
        String var = env.get(envVar);
        if (var == null) {
            throw new IllegalArgumentException(String.format("Unable to find value for required environment var '%s'", envVar));
        }
        return var;
    }
}
