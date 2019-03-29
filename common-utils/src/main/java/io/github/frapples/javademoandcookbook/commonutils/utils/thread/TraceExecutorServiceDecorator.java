package io.github.frapples.javademoandcookbook.commonutils.utils.thread;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/25
 */
@Slf4j
public class TraceExecutorServiceDecorator implements ExecutorService {

    interface ExcludeFunctions {
        <T> Future<T> submit(Callable<T> task);
        <T> Future<T> submit(Runnable task, T result);
        Future<?> submit(Runnable task);
    }
    @Delegate(excludes = ExcludeFunctions.class)
    private final ExecutorService executorService;

    public TraceExecutorServiceDecorator(ExecutorService executorService) {
        this.executorService = executorService;
    }


    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(wrap(task, clientTrace()));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return executorService.submit(wrap(task, clientTrace()), result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return executorService.submit(wrap(task, clientTrace()));
    }

    private static Runnable wrap(Runnable task, Exception clientStack) {
        return () -> {
            try {
                task.run();
            } catch (Exception e) {
                logException(clientStack);
                e.addSuppressed(clientStack);
                throw e;
            }
        };
    }

    private static <T> Callable<T> wrap(Callable<T> task, Exception clientStack) {
        return () -> {
            try {
                return task.call();
            } catch (Exception e) {
                logException(clientStack);
                e.addSuppressed(clientStack);
                throw e;
            }
        };
    }

    private static void logException(Exception e) {
        // log.error("", e);
    }

    private static SubmitTaskTraceException clientTrace() {
        return new SubmitTaskTraceException("Client stack trade");
    }

}
