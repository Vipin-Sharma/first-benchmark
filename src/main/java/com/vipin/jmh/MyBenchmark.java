/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

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
