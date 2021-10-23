package com.company.server;

import com.company.server.dispatchers.Dispatcher;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class Connection {
    static int BUFF_SIZE = 256;
    private DatagramSocket socket;

    public Connection(int port, Dispatcher dispatcher) throws SocketException {
        socket = new DatagramSocket(port);
        socket.setReuseAddress(true);
        LinkedBlockingQueue<byte[]> receiveQueue = new LinkedBlockingQueue<>();
        (new PacketReceiver(socket, receiveQueue)).start();
        (new PacketDispatcher(socket, receiveQueue, dispatcher)).start();
    }

    public void sendTo(Connection connection, byte[] message) {
        try {
            DatagramPacket packet = new DatagramPacket(message, message.length);
            packet.setAddress(InetAddress.getByName("localhost"));
            packet.setPort(connection.socket.getLocalPort());
            synchronized (this) {
                socket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
