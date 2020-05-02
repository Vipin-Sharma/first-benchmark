package com.vipin.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import static java.util.EnumSet.noneOf;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class EnumSetBenchmark {

    enum COLOUR {
        GREEN, RED, WHITE, YELLOW, BLACK, PINK, PEACH, GRAY
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(EnumSetBenchmark.class.getSimpleName())
                .threads(1)
                .forks(1)
                .build();

        new Runner(opt).run();
    }


    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfWith1Arguments() {
        return of(COLOUR.GREEN);
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfWith2Arguments() {
        return of(COLOUR.GREEN, COLOUR.BLACK);
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfWith3Arguments() {
        return of(COLOUR.GREEN, COLOUR.BLACK, COLOUR.GRAY);
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfWith4Arguments() {
        return of(COLOUR.GREEN, COLOUR.BLACK, COLOUR.GRAY, COLOUR.PEACH);
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfWith5Arguments() {
        return of(COLOUR.GREEN, COLOUR.BLACK, COLOUR.GRAY, COLOUR.PEACH, COLOUR.PINK);
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfWith8Arguments() {
        return of(COLOUR.GREEN, COLOUR.BLACK, COLOUR.GRAY, COLOUR.PEACH, COLOUR.PINK, COLOUR.RED, COLOUR.WHITE, COLOUR.YELLOW);
    }

    @SafeVarargs
    public static <E extends Enum<E>> EnumSet<E> of(E first, E... rest) {
        EnumSet<E> result = noneOf(first.getDeclaringClass());
        result.add(first);
        for (E e : rest)
            result.add(e);
        return result;
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfUpdatedWith1Arguments() {
        return ofUpdated(COLOUR.GREEN);
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfUpdatedWith2Arguments() {
        return ofUpdated(COLOUR.GREEN, COLOUR.BLACK);
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfUpdatedWith3Arguments() {
        return ofUpdated(COLOUR.GREEN, COLOUR.BLACK, COLOUR.GRAY);
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfUpdatedWith4Arguments() {
        return ofUpdated(COLOUR.GREEN, COLOUR.BLACK, COLOUR.GRAY, COLOUR.PEACH);
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfUpdatedWith5Arguments() {
        return ofUpdated(COLOUR.GREEN, COLOUR.BLACK, COLOUR.GRAY, COLOUR.PEACH, COLOUR.PINK);
    }

    /*@Benchmark*/
    public static EnumSet<COLOUR> testEnumSetOfUpdatedWith8Arguments() {
        return ofUpdated(COLOUR.GREEN, COLOUR.BLACK, COLOUR.GRAY, COLOUR.PEACH, COLOUR.PINK, COLOUR.RED, COLOUR.WHITE, COLOUR.YELLOW);
    }

    @SafeVarargs
    public static <E extends Enum<E>> EnumSet<E> ofUpdated(E first, E... rest) {
        EnumSet<E> result = noneOf(first.getDeclaringClass());
        result.add(first);
        result.addAll(Arrays.asList(rest));
        return result;
    }
}
