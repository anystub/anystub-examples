package org.anystub.examples;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SourceSystem sourceSystem = new SourceSystemImpl();
        Worker worker = new Worker(sourceSystem);
        String s = worker.processedData();
        System.out.printf("processed response: %d", s);
    }
}