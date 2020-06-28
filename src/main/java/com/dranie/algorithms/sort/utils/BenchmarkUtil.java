package com.dranie.algorithms.sort.utils;

import com.dranie.algorithms.sort.Sort;
import com.dranie.algorithms.utils.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;

/**
 * 性能测试工具类
 *
 * @author dranfree
 * @since 2020.05.30
 */
@Slf4j
public abstract class BenchmarkUtil {

    /**
     * 单个算法性能测试
     *
     * @param algo
     */
    public static void benchmark(Sort algo) {
        String name = algo.getClass().getSimpleName();
        Arrays.asList(100, 1000, 10000, 100000, 1000000).forEach(scale -> {
            log.info("[{}] 性能测试，数据量：{}", name, scale);
            int[] a1 = createRandom(scale);
            log.info("[{}] 随机...消耗时间：{}", name, elapsedTime(algo, a1));
            int[] a2 = createSorted(scale);
            log.info("[{}] 有序...消耗时间：{}", name, elapsedTime(algo, a2));
            int[] a3 = createInvert(scale);
            log.info("[{}] 反序...消耗时间：{}", name, elapsedTime(algo, a3));
            int[] a4 = createEquals(scale);
            log.info("[{}] 等值...消耗时间：{}", name, elapsedTime(algo, a4));
        });
    }

    /**
     * 性能测试
     *
     * @param scale 数据量
     * @param algos 算法列表
     */
    public static void benchmark(int scale, Sort... algos) {
        Arrays.asList(algos).forEach(algo -> {
            String name = algo.getClass().getSimpleName();
            int[] a1 = createRandom(scale);
            log.info("[{}] 性能测试，随机...消耗时间：{}", name, elapsedTime(algo, a1));
            int[] a2 = createSorted(scale);
            log.info("[{}] 性能测试，有序...消耗时间：{}", name, elapsedTime(algo, a2));
            int[] a3 = createInvert(scale);
            log.info("[{}] 性能测试，反序...消耗时间：{}", name, elapsedTime(algo, a3));
            int[] a4 = createEquals(scale);
            log.info("[{}] 性能测试，等值...消耗时间：{}", name, elapsedTime(algo, a4));
        });
    }

    /**
     * 和 JDK 自带排序进行比较
     *
     * @param algo
     */
    public static void fuckingJDK(Sort algo) {
        String name = algo.getClass().getSimpleName();
        Arrays.asList(100, 1000, 10000, 100000, 1000000).forEach(scale -> {
            log.info("[{}] 性能测试，数据量：{}", name, scale);
            int[] a1 = createRandom(scale);
            int[] a1_ = new int[a1.length];
            System.arraycopy(a1, 0, a1_, 0, a1.length);
            log.info("[{}] 随机...You:{}, JDK:{}", name, elapsedTime(algo, a1), elapsedTime(Arrays::sort, a1_));
            int[] a2 = createSorted(scale);
            int[] a2_ = new int[a2.length];
            System.arraycopy(a2, 0, a2_, 0, a2.length);
            log.info("[{}] 有序...You:{}, JDK:{}", name, elapsedTime(algo, a2), elapsedTime(Arrays::sort, a2_));
            int[] a3 = createInvert(scale);
            int[] a3_ = new int[a3.length];
            System.arraycopy(a3, 0, a3_, 0, a3.length);
            log.info("[{}] 反序...You:{}, JDK:{}", name, elapsedTime(algo, a3), elapsedTime(Arrays::sort, a3_));
            int[] a4 = createEquals(scale);
            int[] a4_ = new int[a4.length];
            System.arraycopy(a4, 0, a4_, 0, a4.length);
            log.info("[{}] 等值...You:{}, JDK:{}", name, elapsedTime(algo, a4), elapsedTime(Arrays::sort, a4_));
        });
    }

    /**
     * 使用给定数组做测试
     *
     * @param a
     * @param algos
     */
    public static void benchmark(int[] a, Sort... algos) {
        Arrays.asList(algos).forEach(algo -> {
            String name = algo.getClass().getSimpleName();
            int[] az = new int[a.length];
            System.arraycopy(a, 0, az, 0, a.length);
            log.info("[{}] 给定数组测试...消耗时间：{}", name, elapsedTime(algo, az));
        });
    }

    private static long elapsedTime(Sort algo, int[] a) {
        long init = System.currentTimeMillis();
        algo.sort(a);
        long done = System.currentTimeMillis();
        Assert.isTrue(SortUtil.isSorted(a));
        return done - init;
    }


    /**
     * 校验算法正确性
     *
     * @param algo
     */
    public static void check(Sort algo) {
        int n = 20;
        String name = algo.getClass().getSimpleName();
        int[] a1 = createRandom(n);
        log.info("[{}] 正确性测试，{}数组...排序之前：{}", name, "随机", a1);
        algo.sort(a1);
        showResult(a1, name, "随机");
        int[] a2 = createSorted(n);
        log.info("[{}] 正确性测试，{}数组...排序之前：{}", name, "有序", a2);
        algo.sort(a2);
        showResult(a2, name, "有序");
        int[] a3 = createInvert(n);
        log.info("[{}] 正确性测试，{}数组...排序之前：{}", name, "反序", a3);
        algo.sort(a3);
        showResult(a3, name, "反序");
        int[] a4 = createEquals(n);
        log.info("[{}] 正确性测试，{}数组...排序之前：{}", name, "等值", a4);
        algo.sort(a4);
        showResult(a4, name, "等值");
    }
    private static void showResult(int[] a, String name, String tag) {
        if (SortUtil.isSorted(a))
            log.info("[{}] 正确性测试，{}数组...排序成功：{}", name, tag, a);
        else
            log.error("[{}] 正确性测试，{}数组...排序错误：{}", name, tag, a);
    }


    private static int[] createRandom(int n) {
        Random rnd = new Random();
        int[] data = new int[n];
        for (int i = 0; i < data.length; i++) {
            data[i] = rnd.nextInt(n);
        }
        return data;
    }
    private static int[] createSorted(int n) {
        int[] a = new int[n];
        for (int i = 0; i < a.length; i++)
            a[i] = i + 1;
        return a;
    }
    private static int[] createInvert(int n) {
        int[] a = new int[n];
        for (int i = 0; i < a.length; i++)
            a[i] = n - i;
        return a;
    }
    private static int[] createEquals(int n) {
        int[] a = new int[n];
        Arrays.fill(a, new Random().nextInt(n));
        return a;
    }
}