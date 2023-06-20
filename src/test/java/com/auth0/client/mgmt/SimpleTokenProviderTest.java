package com.auth0.client.mgmt;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

public class SimpleTokenProviderTest {

    @Test
    public void throwsWhenTokenNull() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> SimpleTokenProvider.create(null));
        assertThat(iae.getMessage(), is("'api token' cannot be null!"));
    }

    @Test
    public void getTokenReturnsToken() throws Exception {
        TokenProvider provider = SimpleTokenProvider.create("token");
        assertThat(provider.getToken(), is("token"));
    }

    @Test
    public void getTokenAsyncReturnsTokenFuture() throws Exception {
        TokenProvider provider = SimpleTokenProvider.create("token");
        CompletableFuture<String> future = provider.getTokenAsync();
        assertThat(future.get(), is("token"));
    }
}
