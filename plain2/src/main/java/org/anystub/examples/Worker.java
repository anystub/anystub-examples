package org.anystub.examples;

import java.io.IOException;


public class Worker {

    private final SourceSystem sourceSystem;

    public Worker(SourceSystem sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String get() throws IOException {
        return "precessed string: "+sourceSystem.get("worker's arg");
    }

}
