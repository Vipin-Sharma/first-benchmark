package com.vipin.jmh;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
public class MyBenchmark {

    /*public static void main(String[] args) {
        *//*new MyBenchmark().testInversionSumForLoop();*//*
        new MyBenchmark().testInversionSumUsingStreams();
        *//*new MyBenchmark().testInversionSumUsingCernColt();*//*
    }*/

    public static void main(String... args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .threads(4)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    public static double[] array;

    static {
        int num_of_elements = 100;
        array = new double[num_of_elements];
        for (int i = 0; i < num_of_elements; i++) {
            array[i] = i+1;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testInversionSumForLoop(Blackhole blackhole){
        double result = 0;
        for (int i = 0; i < array.length; i++) {
            result += 1.0/array[i];
        }
        blackhole.consume(result);
        /*System.out.println("Result testForLoopInversionSum " + result);*/
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testInversionSumUsingStreams(Blackhole blackhole){
        double result = 0;
        result = Arrays.stream(array).map(d -> 1/d).sum();
        blackhole.consume(result);
        /*System.out.println(Result testInversionSumUsingStreams " + result);*/
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testInversionSumUsingCernColt(Blackhole blackhole){
        double result = Descriptive.sumOfInversions(new DoubleArrayList(array), 0, array.length-1);
        blackhole.consume(result);
        /*System.out.println("Result testInversionSumUsingCernColt " + result);*/
    }

}

/**
 * Results of above test
 * Benchmark                                  Mode  Cnt    Score    Error  Units
 * MyBenchmark.testInversionSumForLoop        avgt  200    1.442 ±  0.085  ns/op
 * MyBenchmark.testInversionSumUsingCernColt  avgt  200  574.431 ±  7.045  ns/op
 * MyBenchmark.testInversionSumUsingStreams   avgt  200  674.014 ± 20.232  ns/op
 */

/**
 * Results after adding @State(Scope.Thread)
 * Benchmark                                  Mode  Cnt    Score    Error  Units
 * MyBenchmark.testInversionSumForLoop        avgt  200    1.647 ±  0.155  ns/op
 * MyBenchmark.testInversionSumUsingCernColt  avgt  200  603.254 ± 22.199  ns/op
 * MyBenchmark.testInversionSumUsingStreams   avgt  200  645.895 ± 20.833  ns/o
 */

/**
 * Results after adding main method having runner, results are from intellij console.
 * Benchmark                                  Mode  Cnt     Score     Error  Units
 * MyBenchmark.testInversionSumForLoop        avgt   20     3.366 ±   0.672  ns/op
 * MyBenchmark.testInversionSumUsingCernColt  avgt   20  1199.520 ±  51.976  ns/op
 * MyBenchmark.testInversionSumUsingStreams   avgt   20  1273.100 ± 127.171  ns/op
 */

/**
 * Command to get JFR
 * java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=defaultrecording=true,dumponexit=true,dumponexitpath=/tmp/MyBenchmark.jfr MyBenchmark
 */

/**
 * Updated results after adding Blackhole.consume
 * Benchmark                                  Mode  Cnt    Score    Error  Units
 * MyBenchmark.testInversionSumForLoop        avgt  200  525.498 ± 10.458  ns/op
 * MyBenchmark.testInversionSumUsingCernColt  avgt  200  517.930 ±  2.080  ns/op
 * MyBenchmark.testInversionSumUsingStreams   avgt  200  582.103 ±  3.261  ns/op
 */
