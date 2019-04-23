package io.github.frapples.javademoandcookbook.springboot.mq;

import static org.junit.Assert.*;

import io.github.frapples.javademoandcookbook.springboot.base.BaseTest;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/10/26
 */
public class SenderTest extends BaseTest {

    @Autowired
    private Sender sender;

    @Test
    @SneakyThrows
    public void send() {
        sender.send();
        Thread.sleep(2 * 1000);
    }
}
