package org.anystub.examples;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public ClientHttpConnector clientHttpConnector() {
        return new ReactorClientHttpConnector();
    }


    @Bean
    public WebClient webClient(ClientHttpConnector clientHttpConnector) {
        return WebClient.builder()
                .exchangeFunction(ExchangeFunctions.create(clientHttpConnector))
                .baseUrl("https://quotes.rest")
                .build();
    }


}
