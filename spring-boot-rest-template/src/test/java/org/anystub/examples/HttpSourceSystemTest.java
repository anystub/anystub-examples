package org.anystub.examples;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.anystub.AnySettingsHttp;
import org.anystub.AnyStubId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WireMockTest(httpPort = 8080)
class HttpSourceSystemTest {


    @Autowired
    HttpSourceSystem httpSourceSystem;

    @Test
    @AnyStubId
    void testSourceSystem() {

        stubFor(WireMock.get("/api/random")
                .willReturn(ok()
                        .withHeader("x-forward", "test")
                        .withHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .withBody("response from a real system")));

        String r = httpSourceSystem.getStrings();

        assertEquals("application/octet-stream: response from a real system", r);


    }
}