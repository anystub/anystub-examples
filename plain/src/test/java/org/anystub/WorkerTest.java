package org.anystub;

import org.anystub.mgmt.BaseManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;


@AnyStubId
class WorkerTest {

    Worker worker;

    @BeforeEach
    void setup() {
        SourceSystemImpl system = new SourceSystemImpl("http://localhohst:8080");
        worker = new Worker(new StubSourceSystem(system));
    }

    @RepeatedTest(10)
    void testWorker() throws IOException {
        String s = worker.get();
        Assertions.assertEquals("!", s.substring(s.length()-1));

        int rand = worker.rand();
        Assertions.assertEquals(1002, rand);
    }
}