package com.company.server.dispatchers;

import com.company.server.Server;

public class MultiDispatcher implements Dispatcher {

    private Server server;

    public MultiDispatcher(Server server) {
        this.server = server;
    }

    @Override
    public void dispatch(byte[] message) {

    }
}
