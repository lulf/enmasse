/*
 * Copyright 2019, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */

package io.enmasse.iot.registry.device;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.eclipse.hono.service.management.OperationResult;
import org.eclipse.hono.service.management.credentials.CommonCredential;
import org.eclipse.hono.service.management.credentials.PasswordCredential;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.enmasse.iot.utils.MoreFutures;
import io.opentracing.Span;
import io.opentracing.noop.NoopSpan;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

/**
 * Tests for {@link AbstractCredentialsManagementService}.
 *
 */
public class AbstractCredentialsManagementServiceTest {

    private Vertx vertx;
    private AbstractCredentialsManagementService service;

    @BeforeEach
    public void setup() {
        this.vertx = Vertx.factory.vertx();
        this.service = new AbstractCredentialsManagementService(null, this.vertx) {

            @Override
            protected Future<OperationResult<Void>> processSet(DeviceKey key, Optional<String> resourceVersion, List<CommonCredential> credentials, Span span) {
                return Future.succeededFuture();
            }

            @Override
            protected Future<OperationResult<List<CommonCredential>>> processGet(DeviceKey key, Span span) {
                return Future.succeededFuture();
            }
        };
    }

    @AfterEach
    public void cleanup() {
        vertx.close();
    }

    /**
     * Test if the abstract implementation detects the invalid credential information.
     * <br>
     * This test checks if the {@link AbstractCredentialsManagementService} detects, and rejects an
     * invalid credential set. For that the actual implementation of the service can simply return
     * {@code null}.
     *
     * @throws Exception if something goes wrong. It should not.
     */
    @Test
    public void testValidationFailure() throws Exception {
        final PasswordCredential invalidCredentials = new PasswordCredential();
        final List<CommonCredential> credentials = Collections.singletonList(invalidCredentials);

        final Future<OperationResult<Void>> f = this.service.processSet("foo", "bar", Optional.empty(), credentials, NoopSpan.INSTANCE);
        final OperationResult<Void> r = MoreFutures.get(f);
        assertThat(r, notNullValue());
        assertThat(r.isError(), Is.is(true));
        assertThat(r.getStatus(), Is.is(HttpURLConnection.HTTP_BAD_REQUEST));
    }

}
