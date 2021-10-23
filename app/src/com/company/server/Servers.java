package com.company.server;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Servers {
    private List<Server> servers;

    public Servers(Server... servers) {
        this.servers = Arrays.asList(servers);
    }

    public void sendPrepareMessage() {
        for (Server server: servers) {
            server.sendPrepareMessage();
        }
    }
}
