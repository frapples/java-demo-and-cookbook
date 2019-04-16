package io.github.frapples.javademoandcookbook.springboot.mq;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/10/26
 */
@Component
public class Sender {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public Sender(@Autowired(required = false) AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @SneakyThrows
    public void send() {
        for (int i = 0; i < 10; i++) {
            sendMsg();
        }
    }

    private void sendMsg() {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("name", "tom");
        msgMap.put("age", "20");
        msgMap.put("isMale", true);
        String json = JSON.toJSONString(msgMap);
        amqpTemplate.convertAndSend("hello", json);
    }

}
