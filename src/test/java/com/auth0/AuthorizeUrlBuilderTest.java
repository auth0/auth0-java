package com.auth0;

import okhttp3.HttpUrl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URLEncoder;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

public class AuthorizeUrlBuilderTest {

    private static final String DOMAIN = "https://domain.auth0.com";
    private static final String CLIENT_ID = "clientId";
    private static final String REDIRECT_URI = "https://domain.auth0.com/callback";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldThrowWhenDomainIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' must be a valid URL!");
        AuthorizeUrlBuilder.newInstance(null, CLIENT_ID, REDIRECT_URI);
    }

    @Test
    public void shouldThrowWhenDomainHasNoScheme() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' must be a valid URL!");
        AuthorizeUrlBuilder.newInstance("me.something.com", CLIENT_ID, REDIRECT_URI);
    }

    @Test
    public void shouldThrowWhenDomainIsNotURL() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' must be a valid URL!");
        AuthorizeUrlBuilder.newInstance("something", CLIENT_ID, REDIRECT_URI);
    }

    @Test
    public void shouldThrowWhenRedirectUriIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'redirect uri' cannot be null!");
        AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, null);
    }

    @Test
    public void shouldThrowWhenClientIdIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        AuthorizeUrlBuilder.newInstance(DOMAIN, null, REDIRECT_URI);
    }

    @Test
    public void shouldGetNewInstance() throws Exception {
        AuthorizeUrlBuilder instance = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI);
        assertThat(instance, is(notNullValue()));
    }

    @Test
    public void shouldBuildValidAuthorizeUrl() throws Exception {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI).build();
        assertThat(url, not(isEmptyOrNullString()));
        assertThat(HttpUrl.parse(url), is(notNullValue()));
        assertThat(HttpUrl.parse(url).scheme(), is("https"));
        assertThat(HttpUrl.parse(url).host(), is("domain.auth0.com"));
        assertThat(HttpUrl.parse(url).pathSegments().size(), is(1));
        assertThat(HttpUrl.parse(url).pathSegments().get(0), is("authorize"));
    }

    @Test
    public void shouldAddResponseTypeCode() throws Exception {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI).build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("response_type"), is("code"));
    }

    @Test
    public void shouldAddClientId() throws Exception {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI).build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("client_id"), is(CLIENT_ID));
    }

    @Test
    public void shouldAddRedirectUri() throws Exception {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI).build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("redirect_uri"), is(REDIRECT_URI));
    }

    @Test
    public void shouldNotEncodeTwiceTheRedirectUri() throws Exception {
        String encodedUrl = URLEncoder.encode("https://www.google.com/?src=her&q=ans", "UTF-8");
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, encodedUrl).build();
        HttpUrl parsed = HttpUrl.parse(url);

        assertThat(parsed.queryParameter("redirect_uri"), is(notNullValue()));
        assertThat(parsed.queryParameter("redirect_uri"), is(not(encodedUrl)));
        assertThat(parsed.encodedQuery(), containsString("redirect_uri=" + encodedUrl));
    }

    @Test
    public void shouldSetConnection() throws Exception {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withConnection("my-connection")
                .build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("connection"), is("my-connection"));
    }

    @Test
    public void shouldThrowWhenConnectionIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection' cannot be null!");
        AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withConnection(null);
    }

    @Test
    public void shouldSetAudience() throws Exception {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withAudience("https://myapi.domain.com/users")
                .build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("audience"), is("https://myapi.domain.com/users"));
    }

    @Test
    public void shouldThrowWhenAudienceIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'audience' cannot be null!");
        AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withAudience(null);
    }

    @Test
    public void shouldSetState() throws Exception {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withState("1234567890")
                .build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("state"), is("1234567890"));
    }

    @Test
    public void shouldThrowWhenStateIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'state' cannot be null!");
        AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withState(null);
    }

    @Test
    public void shouldSetScope() throws Exception {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withScope("profile email contacts")
                .build();
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("scope"), is("profile email contacts"));
    }

    @Test
    public void shouldThrowWithStateIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'state' cannot be null!");
        AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withState(null);
    }

}