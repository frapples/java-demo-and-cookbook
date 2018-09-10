package io.github.frapples.javademoandcookbook.demoandcase.demo.eventloop;

import io.github.frapples.javademoandcookbook.demoandcase.demo.eventloop.synclib.Async;
import java.time.Instant;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/5
 */
public class Main {

    public static void main(String[] args) {
        EventLoop.EVENT_LOOP.addCompleted(Main::demo);
        EventLoop.EVENT_LOOP.run();
    }

    public static void demo() {
        System.out.println(Instant.now().getEpochSecond());
        Async.sleep(4 * 1000, () -> System.out.println(Instant.now().getEpochSecond() + ":b"));
        Async.sleep(5 * 1000, () -> System.out.println(Instant.now().getEpochSecond() + ":a"));
        Async.httpGet("http://www.bing.com", (r, e) -> System.out.println(r.length()));
        Async.httpGet("http://www.baidu.com", (r, e) -> System.out.println(r.length()));
    }
}
