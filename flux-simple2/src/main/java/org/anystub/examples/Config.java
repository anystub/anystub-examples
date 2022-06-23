package org.anystub.examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Configuration
public class Config {

    @Bean
    public WebClient webClient(@Autowired(required = false) ExchangeFunction exchangeFunction) {
        return WebClient.builder()
                .exchangeFunction(exchangeFunction) // for production code it is going to be null then the builder creates webclient with default exchange function
                .baseUrl("https://quotes.rest")
                .build();
    }

//    @Bean
//    public WebClient webClient(Optional<ExchangeFunction> exchangeFunction) {
//        return WebClient.builder()
//                .exchangeFunction(exchangeFunction.orElse(null)) // if pass null builder create webclient with default exchange function for production code
//                .baseUrl("https://quotes.rest")
//                .build();
//    }
//

}
