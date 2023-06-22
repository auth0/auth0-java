package com.auth0.client.mgmt;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.auth0.AssertsUtil.verifyThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleTokenProviderTest {

    @Test
    public void throwsWhenTokenNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> SimpleTokenProvider.create(null),
            "'api token' cannot be null!");
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
