package com.auth0.net.client;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Auth0HttpRequestTest {

    @Test
    public void headersAreDefensive() {
        Map<String, String> headers = new HashMap<>();
        headers.put("name", "value");

        Auth0HttpRequest request = Auth0HttpRequest.newBuilder("url", HttpMethod.POST)
            .withHeaders(headers)
            .build();

        headers.put("name", "UPDATED");

        assertThat(request.getHeaders().get("name"), is("value"));
    }
}
