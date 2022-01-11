package com.company.server;

import java.util.Arrays;
import java.util.List;

public class Servers {
    private List<Server> servers;

    public Servers(Server... servers) {
        this.servers = Arrays.asList(servers);
    }

    public void sendPrepareMessage(Long value) {
        for (Server server: servers) {
            if(server.isLeader()) {
                server.sendPrepareMessage(value);
            }
        }
    }
}
