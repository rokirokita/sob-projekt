package com.company.server;

import com.company.messages.*;
import com.company.server.dispatchers.MultiDispatcher;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class Server {

    private Connection connection;
    private List<Server> pools;
    private boolean leader;
    private Long currentId = Long.valueOf(0);
    private Long currentValue;

    private HashMap<Long, List<Long>> receivedPromiseValues = new HashMap<>();

    public Server(int port, List<Server> pools) throws SocketException {
        this(port, pools, false);
    }
    public Server(int port, List<Server> pools, boolean leader) throws SocketException {
        this.leader = leader;
        this.pools = pools;
        connection = new Connection(port, new MultiDispatcher(this));
        this.pools.add(this);
    }

    public void sendPrepareMessage(Long currentValue) {
        if(!this.leader){
            return;
        }
        this.currentValue = currentValue;
        this.currentId += 1;
        for (Server server: pools) {
            if (server.equals(this)) {
                continue;
            }
            this.receivedPromiseValues.put(this.currentId, new ArrayList<>());
            this.connection.sendTo(server.getConnection(), MessageSerializator.serialize(new Prepare(this.currentId)));
        }
    }

    public void trySendPromiseMessage(Long sentId) {
        if(this.currentId == null || sentId > this.currentId) {
            for (Server server: this.pools) {
                if (server.equals(this) || !server.isLeader()) {
                    continue;
                }
                if(this.currentValue != null) {
                    this.connection.sendTo(server.getConnection(), MessageSerializator.serialize(new Promise(sentId, this.currentId, this.currentValue)));
                } else {
                    this.connection.sendTo(server.getConnection(), MessageSerializator.serialize(new Promise(sentId)));
                }
            }
            this.currentId = sentId;
        }
    }

    public void trySendAcceptMessage(Long sentId) {
        if(!this.leader || !sentId.equals(this.currentId)){
            return;
        }
        if(this.receivedPromiseValues.containsKey(sentId)) {
            List<Long> raValues = this.receivedPromiseValues.get(sentId);
            raValues.add(null);
        } else {
            List<Long> raValues = new ArrayList<>();
            raValues.add(null);
            this.receivedPromiseValues.put(sentId, raValues);
        }
        sendAcceptMessage(sentId);
    }

    public void trySendAcceptMessage(Long sentId, Long value) {
        if(!this.leader || !sentId.equals(this.currentId)){
            return;
        }
        if(this.receivedPromiseValues.containsKey(sentId)) {
            List<Long> raValues = this.receivedPromiseValues.get(sentId);
            raValues.add(value);
        } else {
            List<Long> raValues = new ArrayList<>();
            raValues.add(value);
            this.receivedPromiseValues.put(sentId, raValues);
        }
        sendAcceptMessage(sentId);
    }

    private void sendAcceptMessage(Long sentId) {
        List<Long> raValues = this.receivedPromiseValues.get(sentId);
        if (raValues.size() > (long) ((this.pools.size()) / 2)) {
            Long value = currentValue;
            for(Long raValue: raValues) {
                if(raValue != null && value < raValue){
                    value = raValue;
                }
            }
            currentValue = value;
            for (Server server : this.pools) {
                if (server.equals(this)) {
                    continue;
                }
                this.connection.sendTo(server.getConnection(), MessageSerializator.serialize(new Accept(sentId, value)));
            }
        }
    }

    public void trySendAcceptedMessage(Long sentId, Long sentValue) {
        if(sentId.equals(this.currentId)) {
            for (Server server : this.pools) {
                if (server.equals(this) || !server.isLeader()) {
                    continue;
                }
                this.currentValue = sentValue;
                this.connection.sendTo(server.getConnection(), MessageSerializator.serialize(new Accepted(sentId, sentValue)));
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public boolean isLeader() {
        return this.leader;
    }

    public Long getCurrentId() {
        return currentId;
    }

    public Long getCurrentValue() {
        return currentValue;
    }
}