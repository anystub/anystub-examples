package org.anystub.examples;

import org.anystub.AnyStubId;
import org.anystub.RequestMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    Worker worker;

    @BeforeEach
    void setup() {
        SourceSystem sourceSystem = new SourceSystemImpl();
        SourceSystem stubSourceSystem = new StubSourceSystem(sourceSystem);
        worker = new Worker(stubSourceSystem);
    }

    @Test
    @AnyStubId
    void testWorker() throws IOException {
        String s = worker.get();
        assertTrue(s.contains("Winning isn't everything"), "unexpected message: "+s);

    }
}