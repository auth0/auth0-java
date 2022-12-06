package com.auth0.client.mgmt;

import com.auth0.client.auth.AuthAPI;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.Response;
import com.auth0.net.TokenRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Date;

import static org.mockito.Mockito.*;

public class ManagedTokenProviderTest {
    private ManagedTokenProvider provider;
    private AuthAPI authAPI;

    @Before
    public void setup() {
        authAPI = Mockito.mock(AuthAPI.class);
        provider = new ManagedTokenProvider(authAPI);
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

        provider.getToken();

        verify(authAPI, times(1)).requestToken(eq("https://domain.auth0.com/api/v2/"));
    }

    @Test
    public void returnsTokenWhenNotExpired() throws Exception {
        when(authAPI.getManagementAPIAudience()).thenReturn("https://domain.auth0.com/api/v2/");
        TokenRequest tokenRequest = mock(TokenRequest.class);
        @SuppressWarnings("unchecked")
        Response<TokenHolder> tokenHolderResponse = mock(Response.class);
        TokenHolder tokenHolder = mock(TokenHolder.class);

        when(tokenHolder.getExpiresAt()).thenReturn(Date.from(Instant.now().plusSeconds(ManagedTokenProvider.LEEWAY + 1)));
        when(tokenRequest.execute()).thenReturn(tokenHolderResponse);
        when(tokenHolderResponse.getBody()).thenReturn(tokenHolder);
        when(authAPI.requestToken("https://domain.auth0.com/api/v2/")).thenReturn(tokenRequest);

        provider.getToken();
        provider.getToken();

        verify(authAPI, times(1)).requestToken(eq("https://domain.auth0.com/api/v2/"));
    }

    @Test
    public void fetchesNewTokenWhenExpired() throws Exception {
        when(authAPI.getManagementAPIAudience()).thenReturn("https://domain.auth0.com/api/v2/");
        TokenRequest tokenRequest = mock(TokenRequest.class);
        @SuppressWarnings("unchecked")
        Response<TokenHolder> tokenHolderResponse = mock(Response.class);
        TokenHolder tokenHolder = mock(TokenHolder.class);

        when(tokenHolder.getExpiresAt()).thenReturn(Date.from(Instant.now().plusSeconds(ManagedTokenProvider.LEEWAY - 1)));
        when(tokenRequest.execute()).thenReturn(tokenHolderResponse);
        when(tokenHolderResponse.getBody()).thenReturn(tokenHolder);
        when(authAPI.requestToken("https://domain.auth0.com/api/v2/")).thenReturn(tokenRequest);

        provider.getToken();
        provider.getToken();

        verify(authAPI, times(2)).requestToken(eq("https://domain.auth0.com/api/v2/"));
    }
}
