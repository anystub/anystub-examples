package org.anystub.examples;

import org.anystub.AnySettingsHttp;
import org.anystub.AnyStubId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class MessageExtractorTest {

    @Autowired
    MessageExtractor messageExtractor;


    @Test
    @AnyStubId
    @AnySettingsHttp
    void testMonoStrippedQuote() {
        Mono<String> lineWithOffset = messageExtractor.getLineWithOffset(3);

        StepVerifier.create(lineWithOffset)
                .expectNextMatches((s)->s.startsWith("as are these disembodied life forms"))
                .expectComplete()
                .verify();

    }
}