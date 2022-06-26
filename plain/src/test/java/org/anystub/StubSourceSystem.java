package org.anystub;

import org.anystub.mgmt.BaseManagerFactory;

import java.io.IOException;

public class StubSourceSystem implements SourceSystem {
    private final SourceSystem real;


    public StubSourceSystem(SourceSystem real) {
        this.real = real;
    }


    @Override
    public String get() throws IOException {
        return BaseManagerFactory
                .locate().request(() -> real.get(),
                        "root");
    }

    /**
     * pay attention: the result of the function depends on
     * internal state of the object, which changed by invocation of the function
     *
     * @param seed should have the same meaning as in super class
     * @return from upstream or from
     */
    @Override
    public Integer rand(int seed) {
        return BaseManagerFactory
                .locate()
                .requestI(() -> real.rand(seed),
                        "rand", Integer.toString(seed));
    }
}
