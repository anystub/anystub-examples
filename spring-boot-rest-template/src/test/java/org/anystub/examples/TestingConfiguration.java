package org.anystub.examples;

import org.anystub.http.StubHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestingConfiguration {

    @Bean
    @Primary
    public HttpClient httpClientTest(@Qualifier("httpClient") HttpClient httpClient) {
        return StubHttpClient.stub(httpClient);
    }

}
