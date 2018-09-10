package io.github.frapples.javademoandcookbook.demoandcase.demo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        final int PORT = 4040;
        Thread serverThread = new Thread(new Server(PORT));
        serverThread.start();

        Client client = new Client("127.0.0.1", PORT);
        Thread.sleep(1000);
        while (true) {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String s = stdin.readLine();
            String r = client.send(s);
            System.out.println(r);
        }
    }
}


