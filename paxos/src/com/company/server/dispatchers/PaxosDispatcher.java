package com.company.server.dispatchers;

import com.company.messages.*;
import com.company.server.Server;

import java.util.Random;

public class PaxosDispatcher implements Dispatcher {

    private Server server;

    public PaxosDispatcher(Server server) {
        this.server = server;
    }

    @Override
    public void dispatch(byte[] messageData) {
        if(server.hasConnectionProblem() && new Random().nextInt()%2 == 0) {
            return;
        }
        Object messageObject = MessageSerializator.deserialize(messageData);
        if(messageObject instanceof Message) {
            Message message = (Message) messageObject;
            if(message instanceof Prepare) {
                server.trySendPromiseMessage(((Prepare) message).getId());
            } else if (message instanceof Promise) {
                if(((Promise) message).hasAcceptedValue()) {
                    server.trySendAcceptMessage(
                            ((Promise) message).getId(),
                            ((Promise) message).getAcceptedValue()
                    );
                } else {
                    server.trySendAcceptMessage(((Promise) message).getId());
                }
            } else if (message instanceof Accept) {
                server.trySendAcceptedMessage(
                        ((Accept) message).getId(),
                        ((Accept) message).getValue()
                );
            }
        }
    }
}
