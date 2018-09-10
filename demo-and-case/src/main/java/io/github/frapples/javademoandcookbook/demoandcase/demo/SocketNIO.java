package io.github.frapples.javademoandcookbook.demoandcase.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketNIO {

    public static void main(String[] args) {
        new NIOServer(4040).run();
    }

}

class NIOServer implements Runnable {

    private int port;

    NIOServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            exec();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exec() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        Selector selector = Selector.open();

        serverSocket.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int n = selector.select(); // Block
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    if (channel != null) {
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                        onAccept(channel);
                    }
                }
                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    onRead(socketChannel);
                }
                it.remove();
            }
        }
    }

    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    private ByteBuffer getBuffer() {
        buffer.clear();
        return buffer;
    }

    private void onRead(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = getBuffer();
        int count;
        // 当可以读到数据时一直循环，通道为非阻塞
        while ((count = socketChannel.read(buffer)) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
            buffer.clear();
        }

        if (count < 0) {
            socketChannel.close();
        }
    }

    private void onAccept(SocketChannel channel) throws IOException {
        System.out.println(channel.socket().getInetAddress() + "/" + channel.socket().getPort());
        ByteBuffer buffer = getBuffer();
        buffer.put("hello\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }
}
