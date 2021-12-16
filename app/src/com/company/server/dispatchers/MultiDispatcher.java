package com.company.server.dispatchers;

import com.company.server.Server;

public class MultiDispatcher implements Dispatcher {

    private Server server;

    private Dispatcher[] dispatchers;

    public MultiDispatcher(Server server) {
        this.server = server;
        this.dispatchers = new Dispatcher[]{new LogDispatcher(server), new PaxosDispatcher(server)};
    }

    @Override
    public void dispatch(byte[] message) {
        for(Dispatcher dispatcher : this.dispatchers) {
            dispatcher.dispatch(message);
        }
    }
}
