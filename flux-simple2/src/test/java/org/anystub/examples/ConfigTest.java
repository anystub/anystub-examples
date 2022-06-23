package org.anystub.examples;

import org.anystub.StubExchangeFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFunction;

@Configuration
class ConfigTest {

    @Bean
    public ExchangeFunction exchangeFunction(){
        return new StubExchangeFunction();
    }


}