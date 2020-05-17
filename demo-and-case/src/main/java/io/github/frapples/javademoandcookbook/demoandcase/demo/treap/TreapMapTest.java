package io.github.frapples.script.lifetask.bootstrap.config;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.apache.commons.lang3.time.StopWatch;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2020/5/16
 */
public class TreapMapTest {

    public static void main(String[] args) {
        int max = 5000_000;
        int[] array = new int[max];
        for (int i = 0; i < max; i++) {
            array[i] = i;
        }
        System.out.println("升序数组：");
        test(array);

        for (int i = 0; i < max; i++) {
            array[i] = max - i;
        }
        System.out.println("降序数组：");
        test(array);

        Random random = new Random();
        for (int i = 0; i < max; i++) {
            array[i] = random.nextInt();
        }
        System.out.println("随机数组：");
        test(array);
    }

    static void test(int[] array) {
        System.out.println("插入：");
        StopWatch sw;
        sw = StopWatch.createStarted();
        Map<Integer, Integer> map = new TreapTreeMap<>();
        for (int i : array) {
            map.put(i, i);
        }
        System.out.println(sw);

        sw = StopWatch.createStarted();
        TreeMap<Integer, Integer> map2 = new TreeMap<>();
        for (int i : array) {
            map2.put(i, i);
        }
        System.out.println(sw);

        System.out.println("查找：");
        sw = StopWatch.createStarted();
        for (int i : array) {
            map.get(i);
        }
        System.out.println(sw);

        sw = StopWatch.createStarted();
        for (int i : array) {
            map2.get(i);
        }
        System.out.println(sw);

        System.out.println("删除");
        sw = StopWatch.createStarted();
        for (int i : array) {
            map.remove(i);
        }
        System.out.println(sw);

        sw = StopWatch.createStarted();
        for (int i : array) {
            map2.remove(i);
        }
        System.out.println(sw);
    }

}
