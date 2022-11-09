package com.auth0.net.client;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HttpResponseTest {

    @Test
    public void headersAreDefensive() {
        Map<String, String> headers = new HashMap<>();
        headers.put("name", "value");

        HttpResponse response = HttpResponse.newBuilder()
            .headers(headers)
            .build();

        headers.put("name", "UPDATED");

        assertThat(response.getHeaders().get("name"), is("value"));
    }
}
