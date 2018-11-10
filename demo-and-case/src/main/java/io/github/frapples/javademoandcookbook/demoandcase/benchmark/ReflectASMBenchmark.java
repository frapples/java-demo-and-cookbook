package io.github.frapples.javademoandcookbook.demoandcase.benchmark;

import com.esotericsoftware.reflectasm.MethodAccess;
import io.github.frapples.javademoandcookbook.commonutils.entity.Point;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-11-6
 */

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class ReflectASMBenchmark {


    private Point p;
    private MethodAccess access;

    private MethodHandle mhGetter;
    private MethodHandle mhSetter;

    private Method mSetter;
    private Method mGetter;

    private int getterIndex;
    private int setterIndex;

    @Setup
    public void init() throws NoSuchMethodException, IllegalAccessException {
        p = new Point();
        access = MethodAccess.get(Point.class);


        MethodType mt = MethodType.methodType(void.class, Integer.class);
        mhSetter = MethodHandles.lookup().findVirtual(p.getClass(), "setX", mt).bindTo(p);
        mt = MethodType.methodType(Integer.class);
        mhGetter = MethodHandles.lookup().findVirtual(p.getClass(), "getX", mt).bindTo(p);

        mSetter = Point.class.getMethod("setX", Integer.class);
        mGetter = Point.class.getMethod("getX");

        getterIndex = access.getIndex("getX");
        setterIndex = access.getIndex("getX");
    }


    @Benchmark
    public void reflectAsmWithOriginGet() {
        MethodAccess access = MethodAccess.get(Point.class);
        access.invoke(p, "setX", 1);
    }

    @Benchmark
    public void reflectAsmStringIndexWithOriginGet() {
        MethodAccess access = MethodAccess.get(Point.class);
        int getterIndex = access.getIndex("getX");
        access.invoke(p, getterIndex);
        int setterIndex = access.getIndex("getX");
        access.invoke(p, setterIndex);
    }


    @Benchmark
    public void reflectAsmIndexWithCacheGet() {
        access.invoke(p, getterIndex);
        access.invoke(p, setterIndex);
    }
    @Benchmark
    public void reflectAsmWithCacheGet() {
        MethodAccess access = methodAccessGet(Point.class);
        access.invoke(p, "setX", 1);
        access.invoke(p, "getX");
    }

    @Benchmark
    public void reflectAsmWithInitGet() {
        access.invoke(p, "setX", 1);
        access.invoke(p, "getX");
    }

    @Benchmark
    public void javaReflectWithOriginGet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Point.class.getMethod("setX", Integer.class).invoke(p, 1);
        Point.class.getMethod("getX").invoke(p);
    }

    @Benchmark
    public void javaReflectWithInitGet() throws InvocationTargetException, IllegalAccessException {
        mSetter.invoke(p, 1);
        mGetter.invoke(p);
    }

    @Benchmark
    public void javaReflectWithCacheGet() throws InvocationTargetException, IllegalAccessException {
        methodGet(Point.class, "setX", Integer.class).invoke(p, 1);
        methodGet(Point.class, "getX").invoke(p);
    }

    @Benchmark
    public void javaMethodHandleWithInitGet() throws Throwable {
        mhSetter.invoke(1);
        mhGetter.invoke();
    }

    @Benchmark
    @Measurement
    public void normalCall() {
        p.setX(1);
        p.getX();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(ReflectASMBenchmark.class.getName())
            .forks(1)
            //.warmupIterations(0)
            //.measurementIterations(1)
            //.measurementTime(TimeValue.seconds(1))
            .build();

        new Runner(opt).run();
    }

    private static ConcurrentHashMap<Class<?>, MethodAccess> cache = new ConcurrentHashMap<>();
    static MethodAccess methodAccessGet(Class<?> clazz) {
        cache.computeIfAbsent(clazz, (c) -> MethodAccess.get(clazz));
        return cache.get(clazz);
    }

    private static ConcurrentHashMap<Key, Method> cacheMethod = new ConcurrentHashMap<>();

    @EqualsAndHashCode(exclude = "args")
    @AllArgsConstructor
    public static class Key {
        public Class<?> clazz;
        public String name;
        public Class<?>[] args;
    }

    static Method methodGet(Class<?> clazz, String name, Class<?>... args) {
        Key key = new Key(clazz, name, args);
        cacheMethod.computeIfAbsent(key, k -> {
            try {
                return k.clazz.getMethod(k.name, k.args);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
        return cacheMethod.get(key);
    }
}
