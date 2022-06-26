package org.anystub;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SourceSystemImpl sourceSystemImpl = new SourceSystemImpl("http://localhost:8081");
        Worker worker = new Worker(sourceSystemImpl);
        String s = worker.get();
        int rand = worker.rand();
        System.out.printf("processed response: %d %s", rand, s);
    }
}