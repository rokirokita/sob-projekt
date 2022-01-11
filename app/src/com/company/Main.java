package com.company;

import com.company.server.Connection;
import com.company.server.Server;
import com.company.server.Servers;
import com.company.server.dispatchers.LogDispatcher;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        try {
            List<Server> connections = new ArrayList<Server>();
            Servers servers = new Servers(
                    new Server(4440, connections, true),
                    new Server(4441, connections),
                    new Server(4442, connections),
                    new Server(4443, connections)
            );
            servers.sendPrepareMessage(2L);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
