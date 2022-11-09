package com.auth0.net.client;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HttpRequestTest {

    @Test
    public void headersAreDefensive() {
        Map<String, String> headers = new HashMap<>();
        headers.put("name", "value");

        HttpRequest request = HttpRequest.newBuilder("url", HttpMethod.POST)
            .headers(headers)
            .build();

        headers.put("name", "UPDATED");

        assertThat(request.getHeaders().get("name"), is("value"));
    }
}
