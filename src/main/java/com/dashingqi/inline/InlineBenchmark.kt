package com.dashingqi.inline

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Threads
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.results.format.ResultFormatType
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class InlineBenchmark {

    private fun foo(block: () -> Unit) {
        block()
    }

    private inline fun fooInline(block: () -> Unit) {
        block()
    }

    @Benchmark
    fun testNoInline() {
        var i = 0
        foo {
            i++
        }
    }

    @Benchmark
    fun testInline() {
        var i = 0
        fooInline {
            i++
        }
    }
}

fun main() {
    val options = OptionsBuilder()
        .include(InlineBenchmark::class.java.simpleName)
        .resultFormat(ResultFormatType.JSON)
        .build()

    Runner(options).run()
}