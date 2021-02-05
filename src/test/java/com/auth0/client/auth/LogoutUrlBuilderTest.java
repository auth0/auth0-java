package com.auth0.client.auth;

import okhttp3.HttpUrl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URLEncoder;

import static com.auth0.client.UrlMatcher.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class LogoutUrlBuilderTest {

    private static final HttpUrl DOMAIN = HttpUrl.parse("https://domain.auth0.com");
    private static final String CLIENT_ID = "clientId";
    private static final String RETURN_TO_URL = "https://domain.auth0.com/callback";

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldThrowWhenBaseUrlIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'base url' cannot be null!");
        LogoutUrlBuilder.newInstance(null, CLIENT_ID, RETURN_TO_URL, true);
    }

    @Test
    public void shouldThrowWhenReturnToURLIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'return to url' cannot be null!");
        LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, null, true);
    }

    @Test
    public void shouldNotThrowWhenClientIdIsNull() {
        LogoutUrlBuilder.newInstance(DOMAIN, null, RETURN_TO_URL, true);
    }

    @Test
    public void shouldGetNewInstance() {
        LogoutUrlBuilder instance = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true);
        assertThat(instance, is(notNullValue()));
    }

    @Test
    public void shouldBuildValidLogoutUrlWithHttp() {
        HttpUrl httpBaseUrl = HttpUrl.parse("http://domain.auth0.com");
        String url = LogoutUrlBuilder.newInstance(httpBaseUrl, CLIENT_ID, RETURN_TO_URL, true).build();
        assertThat(url, isUrl("http", "domain.auth0.com", "/v2/logout"));
    }

    @Test
    public void shouldBuildValidLogoutUrlWithHttps() {
        HttpUrl httpsBaseUrl = HttpUrl.parse("https://domain.auth0.com");
        String url = LogoutUrlBuilder.newInstance(httpsBaseUrl, CLIENT_ID, RETURN_TO_URL, true).build();
        assertThat(url, isUrl("https", "domain.auth0.com", "/v2/logout"));
    }

    @Test
    public void shouldAddReturnToURL() {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true).build();
        assertThat(url, hasQueryParameter("returnTo", RETURN_TO_URL));
    }

    @Test
    public void shouldNotEncodeTwiceTheReturnToURL() throws Exception {
        String encodedUrl = URLEncoder.encode("https://www.google.com/?src=her&q=ans", "UTF-8");
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, encodedUrl, true).build();
        assertThat(url, encodedQueryContains("returnTo=" + encodedUrl));
    }

    @Test
    public void shouldNotAddClientId() {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, false).build();
        assertThat(url, hasQueryParameter("client_id", null));
    }

    @Test
    public void shouldAddClientId() {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true).build();
        assertThat(url, hasQueryParameter("client_id", CLIENT_ID));
    }

    @Test
    public void shouldUseFederated() {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true)
                .useFederated(true)
                .build();
        assertThat(url, hasQueryParameter("federated", ""));
    }

    @Test
    public void shouldNotUseFederated() {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true)
                .useFederated(false)
                .build();
        assertThat(url, hasQueryParameter("federated", null));
    }

}
