package com.company.server;

import java.util.List;

public class Connections {
    private Connection owner;
    private List<Connection> connections;

    public Connections(Connection owner, List<Connection> connections) {
        this.owner = owner;
        this.connections = connections;
    }
}
