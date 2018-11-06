package io.github.frapples.javademoandcookbook.demoandcase.benchmark;

import com.esotericsoftware.reflectasm.MethodAccess;
import io.github.frapples.javademoandcookbook.commonutils.entity.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-11-6
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ReflectASMBenchmark {

    @Benchmark
    @Warmup(iterations = 1)
    public void reflectAsm() {
        Point p = new Point();
        MethodAccess access = MethodAccess.get(Point.class);
        access.invoke(p, "setX", 1);
    }

    @Benchmark
    @Warmup(iterations = 1)
    public void reflectAsmIndex() {
        Point p = new Point();
        MethodAccess access = MethodAccess.get(Point.class);
        int getterIndex = access.getIndex("getX");
        access.invoke(p, getterIndex);
    }

    @Benchmark
    @Warmup(iterations = 1)
    public void javaReflect() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Point p = new Point();
        Point.class.getMethod("getX").invoke(p);
    }
}
