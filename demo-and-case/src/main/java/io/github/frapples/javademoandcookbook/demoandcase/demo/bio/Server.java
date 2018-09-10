package io.github.frapples.javademoandcookbook.demoandcase.demo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Server implements Runnable {

    private int port;
    private Executor executor = Executors.newFixedThreadPool(10);

    Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(() -> serverRequestHandler(socket));
            }
        } catch (IOException e) {
            log.info("", e);
        }
    }

    private void serverRequestHandler(Socket socket1) {
        try (Socket socket = socket1) {
            log.info(socket.getInetAddress().toString() + ":" + socket.getPort());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");

            while (true) {
                String s = in.readLine();
                if (s == null) {
                    return;
                }
                 out.write(s + "\n");
                 out.flush();
            }
        } catch (IOException e) {
            log.info("", e);
        }
    }
}
