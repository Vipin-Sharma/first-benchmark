package com.vipin.jmh;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

public class MyBenchmark {

    /*public static void main(String[] args) {
        new MyBenchmark().testInversionSumForLoop();
        new MyBenchmark().testInversionSumUsingStreams();
        new MyBenchmark().testInversionSumUsingCernColt();
    }*/


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
    public void testInversionSumForLoop(){
        double result = 0;
        for (int i = 0; i < array.length; i++) {
            result += 1.0/array[i];
        }
        /*System.out.println("Result testForLoopInversionSum " + result);*/
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testInversionSumUsingStreams(){
        double result = 0;
        result = Arrays.stream(array).map(d -> 1/d).sum();
        /*System.out.println(Result testInversionSumUsingStreams " + result);*/
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testInversionSumUsingCernColt(){
        double result = Descriptive.sumOfInversions(new DoubleArrayList(array), 0, array.length-1);
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
