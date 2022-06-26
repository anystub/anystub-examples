package org.anystub.examples;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpSourceSystem {

    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8080/api/random";


    public HttpSourceSystem(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getStrings() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);

        return forEntity.getHeaders().getFirst("Content-Type") +": "+ forEntity.getBody();
    }
}
