package org.itmo;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.Z_Result;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "Correct")
@Outcome(id = "false", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Incorrect")
@State
public class ParBFSTest {
    private final int size = 100000;
    private final int edges = 1000000;
    private Graph graph = new RandomGraphGenerator()
            .generateGraph(new Random(42), size, edges);

    private AtomicIntegerArray visited1;
    private AtomicIntegerArray visited2;
    private AtomicIntegerArray visited3;
    private AtomicIntegerArray visited4;
    private AtomicIntegerArray visited5;

    @Actor public void actor1() {
        visited1 = graph.parallelBFS(0);
    }
    @Actor public void actor2() {
        visited2 = graph.parallelBFS(0);
    }
    @Actor public void actor3() {
        visited3 = graph.parallelBFS(0);
    }
    @Actor public void actor4() {
        visited4 = graph.parallelBFS(0);
    }
    @Actor public void actor5() {
        visited5 = graph.parallelBFS(0);
    }

    @Arbiter
    public void arbiter(Z_Result zResult) {
        zResult.r1 = Arrays.stream(new AtomicIntegerArray[]{visited1, visited2, visited3, visited4, visited5})
                .allMatch(v -> v.length() == size);
    }
}
