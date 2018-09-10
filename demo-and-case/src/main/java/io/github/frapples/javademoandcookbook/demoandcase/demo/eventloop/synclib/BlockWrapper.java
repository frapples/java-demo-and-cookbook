package io.github.frapples.javademoandcookbook.demoandcase.demo.eventloop.synclib;

import io.github.frapples.javademoandcookbook.demoandcase.demo.eventloop.Event;
import io.github.frapples.javademoandcookbook.demoandcase.demo.eventloop.EventLoop;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/6
 */
public class BlockWrapper {

    public static final BlockWrapper INSTANCE = new BlockWrapper();

    private ExecutorService executor = Executors.newCachedThreadPool();

    private BlockWrapper() {
    }

    public <T> void addToEvent(Callable<T> blockOperation, Consumer<T> callback) {
        Future<T> future = executor.submit(blockOperation);
        EventLoop.EVENT_LOOP.addEvent(futureToEvent(future, callback));
    }

    public void quit() {
        this.executor.shutdown();
    }


    private <T> Event futureToEvent(Future<T> future, Consumer<T> callback) {
        return new Event() {
            @Override
            public boolean isComplete() {
                return future.isDone();
            }

            @Override
            public void runCallback() {
                try {
                    callback.accept(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
