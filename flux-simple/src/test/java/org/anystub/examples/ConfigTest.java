package org.anystub.examples;

import org.anystub.StubClientHttpConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ClientHttpConnector;

@Configuration
class ConfigTest {
    @Primary
    @Bean
    public ClientHttpConnector clientHttpConnectorTest(@Qualifier("clientHttpConnector") ClientHttpConnector clientHttpConnector) {
        return new StubClientHttpConnector(clientHttpConnector);
    }


}