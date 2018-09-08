package io.github.frapples.utilscookbook.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDemo {
    public static void main(String[] args) throws InterruptedException {
        final int PORT = 4040;
        new Thread(new Server(PORT)).start();
        Thread.sleep(1000);
        client(PORT);
    }


    public static void client(int port) {
        try(Socket socket = new Socket("127.0.0.1", port)) {
            PrintWriter pw = new PrintWriter(
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
            pw.println("Hello!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class Server implements Runnable {
    private int port;
    Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket client = serverSocket.accept();
                new Thread(() -> serverRequestHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serverRequestHandler(Socket client) {
        try {
            System.out.println(client.getInetAddress().toString() + ":" + client.getPort());
            BufferedReader out = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
            String s = out.readLine();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
