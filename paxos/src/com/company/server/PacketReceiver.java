package com.company.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

public class PacketReceiver extends Thread {

    private DatagramSocket socket;
    private BlockingQueue<byte[]> queue;
    private DatagramPacket receivePacket;

    public PacketReceiver(DatagramSocket socket, BlockingQueue<byte[]> queue) {
        this.socket = socket;
        this.queue = queue;
        receivePacket = new DatagramPacket(new byte[Connection.BUFF_SIZE], Connection.BUFF_SIZE);
    }

    @Override
    public void run() {
        while (true) {
            try {
                socket.receive(receivePacket);
                if (receivePacket.getLength() > Connection.BUFF_SIZE) {
                    throw new IOException("message too big " + receivePacket.getLength());
                }
                queue.put(receivePacket.getData().clone());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
