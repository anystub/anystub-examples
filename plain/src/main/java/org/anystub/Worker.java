package org.anystub;

import java.io.IOException;


public class Worker {

    private final SourceSystem sourceSystem;

    public Worker(SourceSystem sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String get() throws IOException {
        return sourceSystem.get() + "!";
    }

    public int rand() {
        return sourceSystem.rand(1000) + 2;
    }
}
