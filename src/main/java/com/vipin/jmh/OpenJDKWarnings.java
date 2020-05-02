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
}

/**
 java -jar target/benchmarks.jar -f 1 -i 5
 Benchmark                                             Mode  Cnt    Score    Error  Units
 OpenJDKWarnings.testAnyMatchOnEmptyList               avgt    5   64.266 ±  3.560  ns/op
 OpenJDKWarnings.testAnyMatchOnNonEmptyList            avgt    5   73.972 ±  6.560  ns/op
 OpenJDKWarnings.testFindFirstIsPresentOnEmptyList     avgt    5   75.822 ±  2.976  ns/op
 OpenJDKWarnings.testFindFirstIsPresentOnNonEmptyList  avgt    5   88.201 ±  4.638  ns/op
 OpenJDKWarnings.testStreamCollectToJoinStrings        avgt    5  400.448 ± 48.007  ns/op
 OpenJDKWarnings.testStringJoin                        avgt    5  213.326 ± 90.728  ns/op


 java -jar target/benchmarks.jar
 # Run complete. Total time: 00:40:33

 Benchmark                                             Mode  Cnt    Score   Error  Units
 OpenJDKWarnings.testAnyMatchOnEmptyList               avgt  200   66.195 ± 1.482  ns/op
 OpenJDKWarnings.testAnyMatchOnNonEmptyList            avgt  200   71.014 ± 1.641  ns/op
 OpenJDKWarnings.testFindFirstIsPresentOnEmptyList     avgt  200   76.845 ± 0.559  ns/op
 OpenJDKWarnings.testFindFirstIsPresentOnNonEmptyList  avgt  200   88.801 ± 0.966  ns/op
 OpenJDKWarnings.testStreamCollectToJoinStrings        avgt  200  264.487 ± 2.117  ns/op
 OpenJDKWarnings.testStringJoin                        avgt  200  154.835 ± 0.757  ns/op


 */
