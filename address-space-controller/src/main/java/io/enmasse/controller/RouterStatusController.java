/*
 * Copyright 2019, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.controller;

import io.enmasse.address.model.AddressSpace;
import io.enmasse.address.model.AddressSpaceSpecConnector;
import io.enmasse.address.model.AddressSpaceSpecConnectorEndpoint;
import io.enmasse.address.model.AddressSpaceStatusConnector;
import io.enmasse.address.model.AddressSpaceStatusRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RouterStatusController {
    private static final Logger log = LoggerFactory.getLogger(RouterStatusController.class);

    private final RouterStatusCache routerStatusCache;

    RouterStatusController(RouterStatusCache routerStatusCache) {
        this.routerStatusCache = routerStatusCache;
    }

}
