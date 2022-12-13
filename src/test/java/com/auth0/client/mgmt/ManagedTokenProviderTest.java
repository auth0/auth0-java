package com.auth0.client.mgmt;

import com.auth0.client.auth.AuthAPI;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.Response;
import com.auth0.net.TokenRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class ManagedTokenProviderTest {
    private ManagedTokenProvider.Builder providerBuilder;
    private AuthAPI authAPI;

    @Before
    public void setup() {
        authAPI = Mockito.mock(AuthAPI.class);
        providerBuilder = ManagedTokenProvider.newBuilder("https://domain.auth0.com/", "clientId", "clientSecret")
            .withAuthAPI(authAPI);
    }

    @Test
    public void throwsWhenLeewayIsNegative() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () ->
            ManagedTokenProvider.newBuilder("domain", "clientId", "clientSecret")
                .withLeeway(-1).build()
        );
        assertThat(iae.getMessage(), is("leeway must be a positive number of seconds"));
    }

    @Test
    public void sameHttpClientCanBeUsed() {
        Auth0HttpClient httpClient = DefaultHttpClient.newBuilder().build();

        ManagedTokenProvider tokenProvider = ManagedTokenProvider.newBuilder("domain", "clientId", "clientSecret")
            .withHttpClient(httpClient)
            .build();
        ManagementAPI api = ManagementAPI.newBuilder("domain", tokenProvider)
            .withTokenProvider(tokenProvider)
            .withHttpClient(httpClient)
            .build();

        assertThat(tokenProvider.getHttpClient(), is(api.getHttpClient()));
    }

    @Test
    public void fetchesTokenInitially() throws Exception {
        when(authAPI.getManagementAPIAudience()).thenReturn("https://domain.auth0.com/api/v2/");
        TokenRequest tokenRequest = mock(TokenRequest.class);
        @SuppressWarnings("unchecked")
        Response<TokenHolder> tokenHolderResponse = mock(Response.class);
        TokenHolder tokenHolder = mock(TokenHolder.class);

        when(tokenRequest.execute()).thenReturn(tokenHolderResponse);
        when(tokenHolderResponse.getBody()).thenReturn(tokenHolder);
        when(authAPI.requestToken("https://domain.auth0.com/api/v2/")).thenReturn(tokenRequest);

        providerBuilder.build().getToken();

        verify(authAPI, times(1)).requestToken(eq("https://domain.auth0.com/api/v2/"));
    }

    @Test
    public void returnsTokenWhenNotExpired() throws Exception {
        int defaultLeeway = 10;

        when(authAPI.getManagementAPIAudience()).thenReturn("https://domain.auth0.com/api/v2/");
        TokenRequest tokenRequest = mock(TokenRequest.class);
        @SuppressWarnings("unchecked")
        Response<TokenHolder> tokenHolderResponse = mock(Response.class);
        TokenHolder tokenHolder = mock(TokenHolder.class);

        when(tokenHolder.getExpiresAt()).thenReturn(Date.from(Instant.now().plusSeconds(defaultLeeway + 1)));
        when(tokenRequest.execute()).thenReturn(tokenHolderResponse);
        when(tokenHolderResponse.getBody()).thenReturn(tokenHolder);
        when(authAPI.requestToken("https://domain.auth0.com/api/v2/")).thenReturn(tokenRequest);

        TokenProvider provider = providerBuilder.build();
        provider.getToken();
        provider.getToken();

        verify(authAPI, times(1)).requestToken(eq("https://domain.auth0.com/api/v2/"));
    }

    @Test
    public void fetchesNewTokenWhenExpired() throws Exception {
        int defaultLeeway = 10;

        when(authAPI.getManagementAPIAudience()).thenReturn("https://domain.auth0.com/api/v2/");
        TokenRequest tokenRequest = mock(TokenRequest.class);
        @SuppressWarnings("unchecked")
        Response<TokenHolder> tokenHolderResponse = mock(Response.class);
        TokenHolder tokenHolder = mock(TokenHolder.class);

        when(tokenHolder.getExpiresAt()).thenReturn(Date.from(Instant.now().plusSeconds(defaultLeeway - 1)));
        when(tokenRequest.execute()).thenReturn(tokenHolderResponse);
        when(tokenHolderResponse.getBody()).thenReturn(tokenHolder);
        when(authAPI.requestToken("https://domain.auth0.com/api/v2/")).thenReturn(tokenRequest);

        TokenProvider provider = providerBuilder.build();
        provider.getToken();
        provider.getToken();

        verify(authAPI, times(2)).requestToken(eq("https://domain.auth0.com/api/v2/"));
    }

    @Test
    public void returnsTokenWhenNotExpiredWithCustomLeeway() throws Exception {
        int leeway = 15;

        when(authAPI.getManagementAPIAudience()).thenReturn("https://domain.auth0.com/api/v2/");
        TokenRequest tokenRequest = mock(TokenRequest.class);
        @SuppressWarnings("unchecked")
        Response<TokenHolder> tokenHolderResponse = mock(Response.class);
        TokenHolder tokenHolder = mock(TokenHolder.class);

        when(tokenHolder.getExpiresAt()).thenReturn(Date.from(Instant.now().plusSeconds(leeway + 1)));
        when(tokenRequest.execute()).thenReturn(tokenHolderResponse);
        when(tokenHolderResponse.getBody()).thenReturn(tokenHolder);
        when(authAPI.requestToken("https://domain.auth0.com/api/v2/")).thenReturn(tokenRequest);

        TokenProvider provider = providerBuilder
            .withLeeway(leeway)
            .build();
        provider.getToken();
        provider.getToken();

        verify(authAPI, times(1)).requestToken(eq("https://domain.auth0.com/api/v2/"));
    }

    @Test
    public void fetchesNewTokenWhenExpiredWithCustomLeeway() throws Exception {
        int leeway = 15;
        when(authAPI.getManagementAPIAudience()).thenReturn("https://domain.auth0.com/api/v2/");
        TokenRequest tokenRequest = mock(TokenRequest.class);
        @SuppressWarnings("unchecked")
        Response<TokenHolder> tokenHolderResponse = mock(Response.class);
        TokenHolder tokenHolder = mock(TokenHolder.class);

        when(tokenHolder.getExpiresAt()).thenReturn(Date.from(Instant.now().plusSeconds(leeway - 1)));
        when(tokenRequest.execute()).thenReturn(tokenHolderResponse);
        when(tokenHolderResponse.getBody()).thenReturn(tokenHolder);
        when(authAPI.requestToken("https://domain.auth0.com/api/v2/")).thenReturn(tokenRequest);

        TokenProvider provider = providerBuilder
            .withLeeway(leeway)
            .build();
        provider.getToken();
        provider.getToken();

        verify(authAPI, times(2)).requestToken(eq("https://domain.auth0.com/api/v2/"));
    }
}
