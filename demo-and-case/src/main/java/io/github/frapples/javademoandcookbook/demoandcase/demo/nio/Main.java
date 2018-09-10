package io.github.frapples.javademoandcookbook.demoandcase.demo.nio;


public class Main {

    public static void main(String[] args) {
        final int PORT = 4040;
        NIOServer server = new NIOServer(PORT);
        server.run();
    }
}

