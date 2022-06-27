package org.anystub.examples;

import org.anystub.AnyStubId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class WorkerTest {

    @Autowired
    Worker worker;


    @Test
    @AnyStubId
    void testWorker() {
        int i = worker.numberOfItems();
        Assertions.assertEquals(51,i);
    }
}