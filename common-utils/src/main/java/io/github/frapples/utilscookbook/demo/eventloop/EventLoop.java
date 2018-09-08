package io.github.frapples.utilscookbook.demo.eventloop;

import io.github.frapples.utilscookbook.demo.eventloop.synclib.BlockWrapper;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/5
 */
public class EventLoop {

    public final static EventLoop EVENT_LOOP = new EventLoop();

    private Queue<Event> eventQueue = new ArrayDeque<>();
    private boolean isQuit = false;

    private EventLoop() {
    }

    public void run() {
        try {
            while (!isQuit) {
                Event e = eventQueue.poll();
                if (e == null) {
                    return;
                } else if (e.isComplete()) {
                    e.runCallback();
                } else {
                    eventQueue.add(e);
                }
            }
        } finally {
            BlockWrapper.INSTANCE.quit();
        }
    }

    public void addEvent(Event e) {
        eventQueue.add(e);
    }

    public void addCompleted(Runnable callback) {
        this.addEvent(new Event() {
            @Override
            public boolean isComplete() {
                return true;
            }

            @Override
            public void runCallback() {
                callback.run();
            }
        });
    }

    public void quit() {
        isQuit = true;
    }
}
