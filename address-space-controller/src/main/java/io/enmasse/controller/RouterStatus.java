/*
 * Copyright 2016-2020, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class RouterStatus {
    private static final Logger log = LoggerFactory.getLogger(RouterStatus.class);

    private final String routerId;
    private final RouterConnections connections;
    private final List<String> neighbours;
    private final long undelivered;

    RouterStatus(String routerId, RouterConnections connections, List<String> neighbours, long undelivered) {
        this.routerId = routerId;
        this.connections = connections;
        this.neighbours = neighbours;
        this.undelivered = undelivered;
    }

    public String getRouterId() {
        return routerId;
    }

    public RouterConnections getConnections() {
        return connections;
    }

    public List<String> getNeighbours() {
        return neighbours;
    }

    public long getUndelivered() {
        return undelivered;
    }
}
