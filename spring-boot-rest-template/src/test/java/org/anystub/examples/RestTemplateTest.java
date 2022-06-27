package org.anystub.examples;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.anystub.AnySettingsHttp;
import org.anystub.AnyStubId;
import org.anystub.RequestMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.anystub.mgmt.BaseManagerFactory.getStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@WireMockTest
public class RestTemplateTest {

    @Autowired
    RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        ;
    }


    @Test
    @AnyStubId(filename = "getWithHeaders")
    @AnySettingsHttp(allHeaders = true)
    public void testGetWithHeaders(WireMockRuntimeInfo wmRuntimeInfo) {
        int port = wmRuntimeInfo.getHttpPort();
        // The static DSL will be automatically configured for you
        stubFor(WireMock.get("/api/random")
                .willReturn(ok()
                .withBody("{\"test\":\"ok\"}")));



        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "tokenxxx");
        ResponseEntity<String> forEntity = restTemplate.exchange(
                "http://localhost:"+port+"/api/random", HttpMethod.GET, new HttpEntity<Object>(headers),
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
    public void testGetWithHeader(WireMockRuntimeInfo wmRuntimeInfo) {
        int port = wmRuntimeInfo.getHttpPort();
        // The static DSL will be automatically configured for you
        stubFor(WireMock.get("/api/random")
                .willReturn(ok()
                        .withBody("{\"test\":\"ok\"}")));


        ResponseEntity<String> forEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/api/random",
                        String.class);
        assertEquals(200, forEntity.getStatusCodeValue());

        assertEquals(1, getStub().times());
        assertTrue(getStub().match().findFirst().get().getKey(2).startsWith("Accept:"));

    }

    @Test
    @AnyStubId
    public void testPost(WireMockRuntimeInfo wmRuntimeInfo) {
        int port = wmRuntimeInfo.getHttpPort();
        // The static DSL will be automatically configured for you
        stubFor(WireMock.post("/api/random")
                .willReturn(jsonResponse("error", 400)));

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity("http://localhost:"+port+"/api/random", null, String.class);
        });
    }

    @Test
    @AnyStubId
    @AnySettingsHttp(bodyTrigger = "random/xxx")
    public void testPostBody(WireMockRuntimeInfo wmRuntimeInfo) {
        int port = wmRuntimeInfo.getHttpPort();
        // The static DSL will be automatically configured for you
        stubFor(WireMock.post("/api/random/xxx")
                .willReturn(jsonResponse("error", 400)));

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity("http://localhost:"+port+"/api/random/xxx", "{test}", String.class);
        });

        assertEquals(1, getStub().times(null, null, null, "{test}"));
    }

    @Test
    @AnyStubId(filename = "postMultiLineBodyTest")
    @AnySettingsHttp(bodyTrigger = "random/xxx")
    public void testPostMultiLineBody(WireMockRuntimeInfo wmRuntimeInfo) {
        int port = wmRuntimeInfo.getHttpPort();
        // The static DSL will be automatically configured for you
        stubFor(WireMock.post("/api/random/xxx")
                .willReturn(jsonResponse("error", 400)));

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity("http://localhost:"+port+"/api/random/xxx", "{test:1,\ntest2:3}", String.class);
        });

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity("http://localhost:"+port+"/api/random/xxx", "{test:1,\n\ntest2:3}", String.class);
        });

        assertEquals(2, getStub().times());
        assertEquals(2, getStub().match().count());
    }

    @Test
    @AnyStubId
    @AnySettingsHttp(bodyTrigger = "random/xxx")
    public void testPostBinaryBody(WireMockRuntimeInfo wmRuntimeInfo) {
        int port = wmRuntimeInfo.getHttpPort();
        // The static DSL will be automatically configured for you
        stubFor(WireMock.post("/api/random/xxx")
                        .willReturn(ok()));

        restTemplate.postForEntity("http://localhost:"+port+
                "/api/random/xxx", new String(new byte[]{1, 2, 3, 4}), String.class);

        assertEquals(1, getStub().matchEx("POST", null, null, "BASE64.*").count());
    }

    @Test
    @AnyStubId
    @AnySettingsHttp(bodyTrigger = "random/xxx", bodyMask = "\\{.+\\}")
    public void testPostMaskedBody(WireMockRuntimeInfo wmRuntimeInfo) {
        int port = wmRuntimeInfo.getHttpPort();
        // The static DSL will be automatically configured for you
        stubFor(WireMock.post("/api/random/xxx")
                .willReturn(jsonResponse("error", 400)));

        for (int i = 0; i < 2; i++) {
            int callNumber = i;
            Assertions.assertThrows(HttpClientErrorException.class, ()-> {
                restTemplate.postForEntity("http://localhost:"+port+
                        "/api/random/xxx", "some text{" + callNumber + "} suffix", String.class);
            });
        }

        assertEquals(2,
                getStub()
                        .history()
                        .filter(d -> d.match_to(null, null, null, "some text... suffix"))
                        .count());

    }

    @Test
    @AnyStubId
    public void testResponseBody(WireMockRuntimeInfo wmRuntimeInfo) {
        stubFor(WireMock.get("/")
                .willReturn(ok()
                        .withHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .withBody("response plain string")));
        ResponseEntity<String> forEntity = restTemplate
                .getForEntity("http://localhost:"+wmRuntimeInfo.getHttpPort(),
                        String.class);
        assertEquals("response plain string", forEntity.getBody());
    }

    static class Response {
        int id;
        String msg;
        LocalDate localDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Response response = (Response) o;

            if (id != response.id) return false;
            if (!Objects.equals(msg, response.msg)) return false;
            return Objects.equals(localDate, response.localDate);
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (msg != null ? msg.hashCode() : 0);
            result = 31 * result + (localDate != null ? localDate.hashCode() : 0);
            return result;
        }
    }
    @Test
    @AnyStubId(requestMode = RequestMode.rmNew)
    public void testObjectResponseBody(WireMockRuntimeInfo wmRuntimeInfo) {
        Response response = new Response();
        response.id = 234;
        response.msg = "test msg";
        response.localDate = LocalDate.of(2022, 2, 2);

        stubFor(WireMock.get("/y")
                .willReturn(ok("{\"id\":234,\"msg\" : \"test msg\",   \"localDate\" : \"2022-02-02\"}")
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
        ResponseEntity<Response> forEntity = restTemplate
                .getForEntity("http://localhost:"+wmRuntimeInfo.getHttpPort()+"/y",
                        Response.class);
        assertEquals(response, forEntity.getBody());
    }

}
