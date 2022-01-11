package com.company.server;

import com.company.server.dispatchers.Dispatcher;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

public class Connection {
    static int BUFF_SIZE = 256;
    private DatagramSocket socket;

    public Connection(int port, Dispatcher dispatcher) throws SocketException {
        socket = new DatagramSocket(port);
        socket.setReuseAddress(true);
        LinkedBlockingQueue<byte[]> receiveQueue = new LinkedBlockingQueue<>();
        PacketReceiver pr = new PacketReceiver(socket, receiveQueue);
        pr.setDaemon(true);
        pr.start();
        PacketDispatcher pd = new PacketDispatcher(socket, receiveQueue, dispatcher);
        pd.setDaemon(true);
        pd.start();
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

    public int getPort() {
        return socket.getLocalPort();
    }
}
