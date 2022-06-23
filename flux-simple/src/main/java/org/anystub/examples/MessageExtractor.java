package org.anystub.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class MessageExtractor {


    static class Success {
        public int total;
    }

    static class Quote {
        public String quote;
    }

    static class Contents {
        public Quote[] quotes;
    }

    static class QuoteResp {
        public Success success;
        public Contents contents;
    }


    final private WebClient webClient;

    public MessageExtractor(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * returns string of joined quotes of the day. skips first `offset` letters in each quote and ellipsis between
     * the sentences if more than one quote
     *
     * @param offset
     * @return
     */
    public Mono<String> getLineWithOffset(int offset) {

        return webClient.get()
                .uri("qod")
                .attribute("language", "en")
                .accept(MediaType.APPLICATION_JSON, MediaType.ALL)
                .retrieve()
                .bodyToMono(String.class)
                .mapNotNull(s -> {
                    ObjectMapper objectMapper = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    try {
                        return objectMapper.readValue(s, QuoteResp.class);
                    } catch (JsonProcessingException e) {
                        return null;
                    }
                })
                .map((QuoteResp a) -> {
                    if (a.success.total < 1 || a.contents.quotes.length < 1) {
                        return "";
                    }

                    return Arrays.stream(a.contents.quotes)
                            .filter(quote -> quote.quote.length() > offset)
                            .map(quote -> quote.quote.substring(offset))
                            .collect(Collectors.joining("..."));
                });

    }


}
