package com.company.server;

import com.company.messages.MessageSerializator;
import com.company.messages.Prepare;
import com.company.server.dispatchers.MultiDispatcher;

import java.net.SocketException;
import java.util.List;
import java.util.logging.Logger;

public class Server {

    private Connection connection;
    private List<Connection> pools;
    private boolean leader;

    public Server(int port, List<Connection> pools) throws SocketException {
        this(port, pools, false);
    }
    public Server(int port, List<Connection> pools, boolean leader) throws SocketException {
        this.leader = leader;
        this.pools = pools;
        connection = new Connection(port, new MultiDispatcher(this));
        this.pools.add(connection);
    }

    public void sendPrepareMessage() {
        if(!this.leader){
            return;
        }
        for (Connection receive: pools) {
            if (receive.equals(connection)) {
                continue;
            }
            connection.sendTo(receive, MessageSerializator.serialize(new Prepare()));
        }
    }
}
