package org.anystub;

import java.io.IOException;

public interface SourceSystem {
    String get() throws IOException;

    Integer rand(int seed);
}
