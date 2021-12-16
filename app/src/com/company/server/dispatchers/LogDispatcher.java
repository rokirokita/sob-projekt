package com.company.server.dispatchers;

import com.company.server.Server;

import java.util.logging.Logger;

public class LogDispatcher implements Dispatcher {
    private Server server;

    public LogDispatcher(Server server) {
        this.server = server;
    }

    @Override
    public void dispatch(byte[] message) {
        Logger.getLogger("log-dispatcher").info("[" + this.server.getConnection().getPort() + "] RECEIVE "+ new String(message));
    }
}
