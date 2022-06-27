package org.anystub.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
class WorkerTest {

    @Autowired
//    Worker worker;
    RestTemplate restTemplate;


    @Test
    void testWorker() {
//        int i = worker.numberOfItems();
//        Assertions.assertEquals(1,i);
        Class<? extends RestTemplate> aClass = restTemplate.getClass();
        System.out.println(""+aClass.getName());
    }
}