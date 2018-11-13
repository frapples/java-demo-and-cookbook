package io.github.frapples.javademoandcookbook.springboot.common.mq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/10/26
 */
public interface RabbitReceiver<E> {

    default <T extends E> void processMessage(Message message, Class<T> clazz) {
        T entity = JSON.parseObject(message.getBody(), clazz);
        process(entity);
    }

    <T extends E> void process(T entity);

}
