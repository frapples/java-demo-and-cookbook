package io.github.frapples.javademoandcookbook.demoandcase.javacase.multithread;

import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;
import io.github.frapples.javademoandcookbook.commonutils.utils.collection.Range;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.StopWatch;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/22
 */
public class ForkJoinPoolDemo {


    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        List<Integer> numbers = Arrays.asList(Iterables.toArray(Iterables.transform(Range.range(1, 1000 * 10000), Long::intValue), Integer.class));
        System.out.println("数据初始化完成");

        {
            StopWatch stopWatch = StopWatch.createStarted();
            Long sum = pool.invoke(new SumTask(numbers));
            System.out.println("ForkJoin:" +  stopWatch.toString());
        }
        {
            StopWatch stopWatch = StopWatch.createStarted();
            Long sum = SumTask.compute(numbers);
            System.out.println("Single Thread:" + stopWatch.toString());
        }


        {
            StopWatch stopWatch = StopWatch.createStarted();
            List<Integer> sum = pool.invoke(new SortTask(numbers));
            System.out.println("ForkJoin:" + stopWatch.toString());

        }

        {
            StopWatch stopWatch = StopWatch.createStarted();
            List<Integer> sum = SortTask.mergeSort(numbers);
            System.out.println("Single Thread:" + stopWatch.toString());
        }




    }


}

@AllArgsConstructor
class SortTask extends RecursiveTask<List<Integer>> {

    private List<Integer> numbers;

    @Override
    protected List<Integer> compute() {
        if (numbers.size() <= 1000) {
            numbers.sort(Integer::compareTo);
            return numbers;
        } else {
            int middle = numbers.size() / 2;
            SortTask taskLeft = new SortTask(numbers.subList(0, middle));
            SortTask taskRight = new SortTask(numbers.subList(middle, numbers.size()));
            taskLeft.fork();
            taskRight.fork();
            return mergeSortedArray(taskLeft.join(), taskRight.join());
        }
    }

    static List<Integer> mergeSort(List<Integer> numbers) {
        if (numbers.size() <= 1000) {
            numbers.sort(Integer::compareTo);
            return numbers;
        } else {
            int middle = numbers.size() / 2;
            List<Integer> left = mergeSort(numbers.subList(0, middle));
            List<Integer> right = mergeSort(numbers.subList(middle, numbers.size()));
            return mergeSortedArray(left, right);
        }
    }

    private static List<Integer> mergeSortedArray(List<Integer> a, List<Integer> b) {
        int[] answer = new int[a.size() + b.size()];
        int i = a.size() - 1, j = b.size() - 1, k = answer.length;
        while (k > 0) {
            answer[--k] = (j < 0 || (i >= 0 && a.get(i) >= b.get(j))) ? a.get(i--) : b.get(j--);
        }
        return Ints.asList(answer);
    }

}



@AllArgsConstructor
class SumTask extends RecursiveTask<Long> {

    private List<Integer> numbers;

    @Override
    protected Long compute() {
        if (numbers.size() < 100) {
            long sum = 0;
            for (Integer number : numbers) {
                sum = sum + number;
            }
            return sum;
        } else {
            int middle = numbers.size() / 2;
            SumTask taskLeft = new SumTask(numbers.subList(0, middle));
            SumTask taskRight = new SumTask(numbers.subList(middle, numbers.size()));
            invokeAll(taskLeft, taskRight);
            // taskLeft.fork();
            // taskRight.fork();
            return taskLeft.join() + taskRight.join();
        }
    }

    static Long compute(List<Integer> numbers) {
        if (numbers.size() < 100) {
            long sum = 0;
            for (Integer number : numbers) {
                sum += number;
            }
            return sum;
        } else {
            int middle = numbers.size() / 2;
            Long left = compute(numbers.subList(0, middle));
            Long right = compute(numbers.subList(middle, numbers.size()));
            return left + right;
        }
    }

}
