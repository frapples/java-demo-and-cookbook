package io.github.frapples.javademoandcookbook.springboot.mq;

import com.alibaba.fastjson.JSONObject;
import io.github.frapples.javademoandcookbook.springboot.common.mq.RabbitReceiver;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/10/26
 */

@Component
public class Receiver implements RabbitReceiver<Map<String, Object>> {

    @RabbitListener(queues = "hello")
    public void processMessage(Message message) {
        RabbitReceiver.super.processMessage(message, JSONObject.class);
    }


    public static class CustomMap extends HashMap<String, Object> {
    }

    @Override
    public <T extends Map<String, Object>> void process(T entity) {
        System.out.println(entity.toString());
    }
}
