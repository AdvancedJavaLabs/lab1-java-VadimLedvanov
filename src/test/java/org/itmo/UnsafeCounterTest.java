package org.itmo;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Outcome(id = "5", expect = Expect.ACCEPTABLE, desc = "All data is correct")
@Outcome(id = "1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Lost data")
@Outcome(id = "2", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Lost data")
@Outcome(id = "3", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Lost data")
@Outcome(id = "4", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Lost data")
@State
public class UnsafeCounterTest {

    private UnsafeCounter counter = new UnsafeCounter();

    @Actor public void actor1() { counter.increment(); }
    @Actor public void actor2() { counter.increment(); }
    @Actor public void actor3() { counter.increment(); }
    @Actor public void actor4() { counter.increment(); }
    @Actor public void actor5() { counter.increment(); }

    @Arbiter
    public void arbiter(I_Result r) {
        r.r1 = counter.get();
    }
}
