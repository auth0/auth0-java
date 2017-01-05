package com.auth0;

import okhttp3.HttpUrl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URLEncoder;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class LogoutUrlBuilderTest {

    private static final String DOMAIN = "https://domain.auth0.com";
    private static final String CLIENT_ID = "clientId";
    private static final String RETURN_TO_URL = "https://domain.auth0.com/callback";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldThrowWhenDomainIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' must be a valid URL!");
        LogoutUrlBuilder.newInstance(null, CLIENT_ID, RETURN_TO_URL, true);
    }

    @Test
    public void shouldThrowWhenDomainHasNoScheme() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' must be a valid URL!");
        LogoutUrlBuilder.newInstance("me.something.com", CLIENT_ID, RETURN_TO_URL, true);
    }

    @Test
    public void shouldThrowWhenDomainIsNotURL() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' must be a valid URL!");
        LogoutUrlBuilder.newInstance("something", CLIENT_ID, RETURN_TO_URL, true);
    }

    @Test
    public void shouldThrowWhenReturnToURLIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'return to url' cannot be null!");
        LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, null, true);
    }

    @Test
    public void shouldNotThrowWhenClientIdIsNull() throws Exception {
        LogoutUrlBuilder.newInstance(DOMAIN, null, RETURN_TO_URL, true);
    }

    @Test
    public void shouldGetNewInstance() throws Exception {
        LogoutUrlBuilder instance = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true);
        assertThat(instance, is(notNullValue()));
    }

    @Test
    public void shouldBuildValidLogoutUrlWithHttp() throws Exception {
        String url = LogoutUrlBuilder.newInstance("http://domain.auth0.com", CLIENT_ID, RETURN_TO_URL, true).build();
        assertThat(url, not(isEmptyOrNullString()));
        assertThat(HttpUrl.parse(url), is(notNullValue()));
        assertThat(HttpUrl.parse(url).scheme(), is("http"));
        assertThat(HttpUrl.parse(url).host(), is("domain.auth0.com"));
        assertThat(HttpUrl.parse(url).pathSegments().size(), is(2));
        assertThat(HttpUrl.parse(url).pathSegments().get(0), is("v2"));
        assertThat(HttpUrl.parse(url).pathSegments().get(1), is("logout"));
    }

    @Test
    public void shouldBuildValidLogoutUrlWithHttps() throws Exception {
        String url = LogoutUrlBuilder.newInstance("https://domain.auth0.com", CLIENT_ID, RETURN_TO_URL, true).build();
        assertThat(url, not(isEmptyOrNullString()));
        assertThat(HttpUrl.parse(url), is(notNullValue()));
        assertThat(HttpUrl.parse(url).scheme(), is("https"));
        assertThat(HttpUrl.parse(url).host(), is("domain.auth0.com"));
        assertThat(HttpUrl.parse(url).pathSegments().size(), is(2));
        assertThat(HttpUrl.parse(url).pathSegments().get(0), is("v2"));
        assertThat(HttpUrl.parse(url).pathSegments().get(1), is("logout"));
    }

    @Test
    public void shouldAddReturnToURL() throws Exception {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true).build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("returnTo"), is(RETURN_TO_URL));
    }

    @Test
    public void shouldNotEncodeTwiceTheReturnToURL() throws Exception {
        String encodedUrl = URLEncoder.encode("https://www.google.com/?src=her&q=ans", "UTF-8");
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, encodedUrl, true).build();
        HttpUrl parsed = HttpUrl.parse(url);

        assertThat(parsed.queryParameter("returnTo"), is(notNullValue()));
        assertThat(parsed.queryParameter("returnTo"), is(not(encodedUrl)));
        assertThat(parsed.encodedQuery(), containsString("returnTo=" + encodedUrl));
    }

    @Test
    public void shouldNotAddClientId() throws Exception {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, false).build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("client_id"), is(nullValue()));
    }

    @Test
    public void shouldAddClientId() throws Exception {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true).build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("client_id"), is(CLIENT_ID));
    }

    @Test
    public void shouldSetAccessToken() throws Exception {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true)
                .withAccessToken("accessToken")
                .build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("access_token"), is("accessToken"));
    }

    @Test
    public void shouldThrowWhenAccessTokenIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'access token' cannot be null!");
        LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true)
                .withAccessToken(null);
    }

    @Test
    public void shouldUseFederated() throws Exception {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true)
                .useFederated(true)
                .build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("federated"), is(notNullValue()));
    }

    @Test
    public void shouldNotUseFederated() throws Exception {
        String url = LogoutUrlBuilder.newInstance(DOMAIN, CLIENT_ID, RETURN_TO_URL, true)
                .useFederated(false)
                .build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("federated"), is(nullValue()));
    }

}