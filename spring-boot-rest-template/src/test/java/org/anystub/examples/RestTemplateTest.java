package org.anystub.examples;

import org.anystub.AnySettingsHttp;
import org.anystub.AnyStubId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.anystub.mgmt.BaseManagerFactory.getStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RestTemplateTest {

    private final RestTemplate restTemplate;

    public RestTemplateTest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Test
    @AnyStubId(filename = "getWithHeaders")
    @AnySettingsHttp(allHeaders = true)
    public void testGetWithHeaders() {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "tokenxxx");
        ResponseEntity<String> forEntity = restTemplate.exchange(
                "http://localhost:8080/api/random", HttpMethod.GET, new HttpEntity<Object>(headers),
                String.class);

        assertEquals(200, forEntity.getStatusCodeValue());

        assertEquals(1, getStub().times());
        String [] v= new String[3];
        v[0] = getStub().match().findFirst().get().getKey(2);
        v[1] = getStub().match().findFirst().get().getKey(3);
        v[2] = getStub().match().findFirst().get().getKey(4);
        Arrays.sort(v);
        assertTrue(v[0].startsWith("Accept:"));
        assertTrue(v[1].startsWith("Authorization:"));
        assertTrue(v[2].startsWith("Content-Type:"));

    }

    @Test
    @AnyStubId(filename = "getWithHeader")
    @AnySettingsHttp(headers = "Accept")
    public void testGetWithHeader() {

        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:8080/api/random", String.class);
        assertEquals(200, forEntity.getStatusCodeValue());

        assertEquals(1, getStub().times());
        assertTrue(getStub().match().findFirst().get().getKey(2).startsWith("Accept:"));

    }

    @Test
    public void testPost() {
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity("http://localhost:8080/api/random", null, String.class);
        });
    }

    @Test
    @AnySettingsHttp(bodyTrigger = "random/xxx")
    public void testPostBody() {
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity("http://localhost:8080/api/random/xxx", "{test}", String.class);
        });

        assertEquals(1, getStub().times(null, null, null, "{test}"));
    }

    @Test
    @AnyStubId(filename = "postMultiLineBodyTest")
    @AnySettingsHttp(bodyTrigger = "random/xxx")
    public void testPostMultiLineBody() {
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity("http://localhost:8080/api/random/xxx", "{test:1,\ntest2:3}", String.class);
        });

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity("http://localhost:8080/api/random/xxx", "{test:1,\n\ntest2:3}", String.class);
        });

        assertEquals(2, getStub().times());
    }

    @Test
    @AnyStubId(filename = "postBinaryBodyTest")
    @AnySettingsHttp(bodyTrigger = "random/xxx")
    public void testPostBinaryBody() {
        restTemplate.postForEntity("http://localhost:8080/api/random/xxx", new String(new byte[]{1, 2, 3, 4}), String.class);

        assertEquals(1, getStub().matchEx("POST", null, null, "BASE64.*").count());
    }

    @Test
    @AnyStubId(filename = "postMaskedBodyTest")
    @AnySettingsHttp(bodyTrigger = "random/xxx", bodyMask = "\\{.+\\}")
    public void testPostMaskedBody() {

        for (int i = 0; i < 2; i++) {
            int callNumber = i;
            Assertions.assertThrows(HttpClientErrorException.class, ()-> {
                restTemplate.postForEntity("http://localhost:8080/api/random/xxx", "some text{" + callNumber + "} suffix", String.class);
            });
        }

        assertEquals(2,
                getStub()
                        .history()
                        .filter(d -> d.match_to(null, null, null, "some text... suffix"))
                        .count());

    }

    @Test
    public void testResponseBody() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://test", String.class);
        assertEquals("test", forEntity.getBody());
    }

}
