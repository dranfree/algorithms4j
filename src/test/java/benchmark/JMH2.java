package benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author GuanShan
 * @since 2020.10.27
 */
// 变量共享的配置（Scope.Thread表示每个线程都享有一份变量的副本）
@State(Scope.Thread)
// 测量维度配置
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
// 预热配置
@Warmup(iterations = 5)
// 实际执行的配置，配置项和预热配置相同。
@Measurement(iterations = 5)
// Fork默认值为10，意味着每个测试要运行10次。
@Fork(1)
public class JMH2 {

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private long[] la;

    // 不同参数的结果往往差别很大
    // 测试不同参数下的不同结果
//    @Param({ "1", "10", "100", "1000", "10000", "100000", "1000000", "10000000", "100000000", "250000000" })
    @Param({ "10000000" })
    int size;

    @Setup
    public void setup() {
        la = new long[size];
    }

    // 计算的复杂度也会影响到基准测试的结果
    public static long f(long x) {
        long quadratic = 42 * x * x + 19 * x + 47;
        return Long.divideUnsigned(quadratic, x + 1);
    }

    @Benchmark
    public void setAll() { Arrays.setAll(la, JMH2::f); }

    @Benchmark
    public void parallelSetAll() { Arrays.parallelSetAll(la, JMH2::f); }
}