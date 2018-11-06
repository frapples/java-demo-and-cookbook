package io.github.frapples.javademoandcookbook.demoandcase.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-11-6
 */
public class BenchmarkMain {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + ReflectASMBenchmark.class.getSimpleName() + ".*")
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
