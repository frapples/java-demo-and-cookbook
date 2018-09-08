package io.github.frapples.utilscookbook.demo.eventloop.synclib;

import io.github.frapples.utilscookbook.demo.eventloop.Event;
import io.github.frapples.utilscookbook.demo.eventloop.EventLoop;
import io.github.frapples.utilscookbook.utils.network.HttpClientUtils;
import java.time.Instant;
import java.util.function.BiConsumer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/5
 */
public class Async {
    public static void sleep(int s, Runnable callback) {
        EventLoop.EVENT_LOOP.addEvent(new Event() {

            private long now = Instant.now().toEpochMilli();

            @Override
            public boolean isComplete() {
                return Instant.now().toEpochMilli() - now >= s;
            }

            @Override
            public void runCallback() {
                callback.run();
            }
        });
    }

    public static void httpGet(String url, BiConsumer<String, Exception> callback) {
        BlockWrapper.INSTANCE.addToEvent(() -> {
            Pair<String, Exception> pair;
            try {
                String result = HttpClientUtils.get(url);
                pair = ImmutablePair.of(result, null);
            } catch (Exception e) {
                pair = ImmutablePair.of(null, e);
            }
            return pair;
        } , (pair) -> callback.accept(pair.getLeft(), pair.getRight()));
    }
}
