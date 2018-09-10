package io.github.frapples.javademoandcookbook.demoandcase.demo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Client {

    private String host;
    private int port;

    public String send(String data) throws IOException {
        try (Socket socket = new Socket(this.host, this.port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            out.write(data + "\n");
            out.flush(); // 刷新后数据才通过网络发送
            return in.readLine();
        } catch (IOException e) {
            throw e;
        }
    }

}
