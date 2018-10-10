/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

public class ResourceCheckerTest {
    ResourceChecker<String> controller;
    Watcher<String> watcher;

    @Before
    public void setup() {
        watcher = mock(Watcher.class);
        controller = new ResourceChecker<>(watcher, Duration.ofMillis(1));
    }

    @Test
    public void testResourcesUpdated() throws Exception {
        List<String> items = Arrays.asList("hello", "there");
        controller.onUpdate(items);
        controller.doWork();
        verify(watcher).onUpdate(eq(items));
    }
}
