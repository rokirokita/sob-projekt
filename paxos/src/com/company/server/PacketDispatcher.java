package com.company.server;

import com.company.server.dispatchers.Dispatcher;

import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

public class PacketDispatcher extends Thread {

    private DatagramSocket socket;
    private BlockingQueue<byte[]> queue;
    private Dispatcher dispatcher;

    public PacketDispatcher(DatagramSocket socket, BlockingQueue<byte[]> queue, Dispatcher dispatcher) {
        this.socket = socket;
        this.queue = queue;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] message = queue.take();
                dispatcher.dispatch(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
