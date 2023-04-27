package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.json.auth.PushedAuthorizationResponse;
import com.auth0.json.auth.TokenHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

public class FormBodyRequestTest {

    private MockServer server;
    private OkHttpClient client;

    private TypeReference<TokenHolder> tokenHolderType;

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        client = new OkHttpClient();
        tokenHolderType = new TypeReference<TokenHolder>() {
        };
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void shouldNotSupportGETMethod() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new FormBodyRequest<>(client, server.getBaseUrl(), "GET", new TypeReference<PushedAuthorizationResponse>() {});
        });
        assertThat(exception.getMessage(), is("application/x-www-form-urlencoded requests do not support the GET method."));
    }
}
