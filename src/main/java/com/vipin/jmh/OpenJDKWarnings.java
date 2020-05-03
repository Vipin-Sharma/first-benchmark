package com.vipin.jmh;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class OpenJDKWarnings {

    public static void main(String... args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(OpenJDKWarnings.class.getSimpleName())
                .threads(4)
                .forks(5)
                .build();

        new Runner(opt).run();
    }

    private static final ArrayList<String> stringList = new ArrayList<>();
    private static final ArrayList<String> emptyStringList = new ArrayList<>();
    private static final String[] stringArray = new String[5];

    static {
        stringArray[0] = "1String";
        stringArray[1] = "2String";
        stringArray[2] = "3String";
        stringArray[3] = "4String";
        stringArray[4] = "5String";

        stringList.add("1String");
        stringList.add("2String");
        stringList.add("3String");
        stringList.add("4String");
        stringList.add("5String");
        stringList.add("");
        stringList.add("");
    }

    @Benchmark
    public void testStringJoin(Blackhole blackhole) {
        blackhole.consume(String.join(",", stringArray));
    }

    @Benchmark
    public void testStreamCollectToJoinStrings(Blackhole blackhole) {
        blackhole.consume(Arrays.stream(stringArray).collect(Collectors.joining(",")));
    }

    @Benchmark
    public void testFindFirstIsPresentOnNonEmptyList(Blackhole blackhole) {
        blackhole.consume(stringList.stream().filter(Objects::nonNull).findFirst().isPresent());
    }

    @Benchmark
    public void testAnyMatchOnNonEmptyList(Blackhole blackhole) {
        blackhole.consume(stringList.stream().anyMatch(Objects::nonNull));
    }

    @Benchmark
    public void testFindFirstIsPresentOnEmptyList(Blackhole blackhole) {
        blackhole.consume(emptyStringList.stream().filter(Objects::nonNull).findFirst().isPresent());
    }

    @Benchmark
    public void testAnyMatchOnEmptyList(Blackhole blackhole) {
        blackhole.consume(emptyStringList.stream().anyMatch(Objects::nonNull));
    }

    @Benchmark
    public void testStreamFilterFindAnyIsPresent(Blackhole blackhole)
    {
        blackhole.consume(stringList.stream().filter(String::isEmpty).findAny().isPresent());
    }

    @Benchmark
    public void testStreamAnyMatch(Blackhole blackhole)
    {
        blackhole.consume(stringList.stream().anyMatch(String::isEmpty));
    }
}

/**
 *
 java -jar target/benchmarks.jar
 # Run complete. Total time: 00:54:05

 Benchmark                                             Mode  Cnt    Score    Error  Units
 OpenJDKWarnings.testFindFirstIsPresentOnEmptyList     avgt  200   73.907 ±  0.350  ns/op
 OpenJDKWarnings.testAnyMatchOnEmptyList               avgt  200   62.057 ±  0.379  ns/op
 OpenJDKWarnings.testFindFirstIsPresentOnNonEmptyList  avgt  200   84.591 ±  0.709  ns/op
 OpenJDKWarnings.testAnyMatchOnNonEmptyList            avgt  200   63.793 ±  0.239  ns/op
 OpenJDKWarnings.testStreamFilterFindAnyIsPresent      avgt  200  122.902 ±  0.673  ns/op
 OpenJDKWarnings.testStreamAnyMatch                    avgt  200   94.402 ±  2.989  ns/op
 OpenJDKWarnings.testStreamCollectToJoinStrings        avgt  200  282.570 ±  6.308  ns/op
 OpenJDKWarnings.testStringJoin                        avgt  200  174.919 ± 13.343  ns/op

 */
