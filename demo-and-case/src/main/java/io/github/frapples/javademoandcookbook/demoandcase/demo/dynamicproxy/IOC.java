package io.github.frapples.javademoandcookbook.demoandcase.demo.dynamicproxy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/27
 */
public class IOC {
    public static AtomicInteger atomicInteger = new AtomicInteger();
    public static <T> T get(Class<T> clazz) {
        return IOCImpl.INSTANCE.get(clazz);
    }
    public static <T> T getAllowNull(Class<T> clazz) {
        return IOCImpl.INSTANCE.getAllowNull(clazz);

    }
    public static <T> void register(Class<?> clazz) {
        IOCImpl.INSTANCE.register(clazz);
    }

    public static void register(Class<?>[] classes) {
        for (Class<?> clazz : classes) {
            IOCImpl.INSTANCE.register(clazz);
        }
    }

    public static void done() {
        IOCImpl.INSTANCE.done();
    }
}

class IOCImpl {
    private final static Object EMPTY = new Object();
    private volatile boolean isDone = false;
    private final ConcurrentHashMap<Class<?>, Object> map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<?>, Class<?>> superClassMap = new ConcurrentHashMap<>();

    final static IOCImpl INSTANCE = new IOCImpl();

    private IOCImpl() {
    }

    <T> T get(Class<T> clazz) {
        T component = this.getAllowNull(clazz);
        if (component == null || !this.isDone) {
            throw new NullPointerException(clazz.getName());
        }
        return component;
    }

    synchronized <T> T getAllowNull(Class<T> clazz) {
        Object object = map.get(clazz);
        if (object == null) {
            return null;
        }

        if (object == EMPTY) {
            try {
                map.put(clazz, clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return (T) map.get(clazz);
    }

    <T> void register(Class<T> clazz) {
        map.put(clazz, EMPTY);



        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            for (Class inter : clazz.getInterfaces()) {
                superClassMap.put(inter, clazz);
            }
            superClassMap.put(c, clazz);
        }
    }

    public void done() {
        this.isDone = true;
    }
}
