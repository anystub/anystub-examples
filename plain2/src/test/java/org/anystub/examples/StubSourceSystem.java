package org.anystub.examples;

import org.anystub.mgmt.BaseManagerFactory;

import java.io.IOException;

public class StubSourceSystem implements SourceSystem{

    final private SourceSystem realSourceSystem;

    public StubSourceSystem(SourceSystem realSourceSystem) {
        this.realSourceSystem = realSourceSystem;
    }

    @Override
    public String get(String arg) throws IOException {
        return BaseManagerFactory
                .locate()
                .request(()-> realSourceSystem.get(arg),
                        arg);
    }
}
