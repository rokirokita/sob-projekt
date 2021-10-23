package com.company.server.dispatchers;

import java.util.logging.Logger;

public class LogDispatcher implements Dispatcher {
    @Override
    public void dispatch(byte[] message) {
        Logger.getLogger("UDPserver-dispatcher").info(new String(message));
    }
}
