package com.auth0.client.auth;

import okhttp3.HttpUrl;
import org.junit.jupiter.api.Test;

import java.net.URLEncoder;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.UrlMatcher.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class AuthorizeUrlBuilderTest {

    private static final HttpUrl DOMAIN = HttpUrl.parse("https://domain.auth0.com");
    private static final String CLIENT_ID = "clientId";
    private static final String REDIRECT_URI = "https://domain.auth0.com/callback";

    @Test
    public void shouldThrowWhenBaseUrlIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(null, CLIENT_ID, REDIRECT_URI),
            "'base url' cannot be null!");
    }

    @Test
    public void shouldThrowWhenRedirectUriIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, null),
            "'redirect uri' cannot be null!");
    }

    @Test
    public void shouldThrowWhenClientIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, null, REDIRECT_URI),
            "'client id' cannot be null!");
    }

    @Test
    public void shouldGetNewInstance() {
        AuthorizeUrlBuilder instance = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI);
        assertThat(instance, is(notNullValue()));
    }

    @Test
    public void shouldBuildValidAuthorizeUrlWithHttp() {
        HttpUrl httpBaseUrl = HttpUrl.parse("http://domain.auth0.com");
        String url = AuthorizeUrlBuilder.newInstance(httpBaseUrl, CLIENT_ID, REDIRECT_URI).build();
        assertThat(url, isUrl("http", "domain.auth0.com", "/authorize"));
    }

    @Test
    public void shouldBuildValidAuthorizeUrlWithHttps() {
        HttpUrl httpsBaseUrl = HttpUrl.parse("https://domain.auth0.com");
        String url = AuthorizeUrlBuilder.newInstance(httpsBaseUrl, CLIENT_ID, REDIRECT_URI).build();
        assertThat(url, isUrl("https", "domain.auth0.com", "/authorize"));
    }

    @Test
    public void shouldAddResponseTypeCode() {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI).build();
        assertThat(url, hasQueryParameter("response_type", "code"));
    }

    @Test
    public void shouldAddClientId() {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI).build();
        assertThat(url, hasQueryParameter("client_id", CLIENT_ID));

    }

    @Test
    public void shouldAddRedirectUri() {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI).build();
        assertThat(url, hasQueryParameter("redirect_uri", REDIRECT_URI));
    }

    @Test
    public void shouldNotEncodeTwiceTheRedirectUri() throws Exception {
        String encodedUrl = URLEncoder.encode("https://www.google.com/?src=her&q=ans", "UTF-8");
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, encodedUrl).build();
        assertThat(url, encodedQueryContains("redirect_uri=" + encodedUrl));
    }

    @Test
    public void shouldSetConnection() {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withConnection("my-connection")
                .build();
        assertThat(url, hasQueryParameter("connection", "my-connection"));
    }

    @Test
    public void shouldThrowWhenConnectionIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withConnection(null),
            "'connection' cannot be null!");
    }

    @Test
    public void shouldSetAudience() {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withAudience("https://myapi.domain.com/users")
                .build();
        assertThat(url, hasQueryParameter("audience", "https://myapi.domain.com/users"));
    }

    @Test
    public void shouldThrowWhenAudienceIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withAudience(null),
            "'audience' cannot be null!");
    }

    @Test
    public void shouldSetState() {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withState("1234567890")
                .build();
        assertThat(url, hasQueryParameter("state", "1234567890"));
    }

    @Test
    public void shouldThrowWhenStateIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withState(null),
            "'state' cannot be null!");
    }

    @Test
    public void shouldSetScope() {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withScope("profile email contacts")
                .build();
        assertThat(url, hasQueryParameter("scope", "profile email contacts"));
    }

    @Test
    public void shouldThrowWhenScopeIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () ->  AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withScope(null),
            "'scope' cannot be null!");
    }

    @Test
    public void shouldSetResponseType() {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withResponseType("token id_token")
                .build();
        assertThat(url, hasQueryParameter("response_type", "token id_token"));
    }

    @Test
    public void shouldThrowWhenResponseTypeIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withResponseType(null),
            "'response type' cannot be null!");
    }

    @Test
    public void shouldSetCustomParameter() {
        String url = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withParameter("name", "value")
                .build();
        assertThat(url, hasQueryParameter("name", "value"));
    }

    @Test
    public void shouldThrowWhenCustomParameterNameIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withParameter(null, "value"),
            "'name' cannot be null!");
    }

    @Test
    public void shouldThrowWhenCustomParameterValueIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withParameter("name", null),
            "'value' cannot be null!");
    }

    @Test
    public void shouldAddOrganizationParameter() {
        String authUrl = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
            .withOrganization("org_abc")
            .build();
        assertThat(authUrl, hasQueryParameter("organization", "org_abc"));
    }

    @Test
    public void shouldThrowWhenOrganizationIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withOrganization(null)
                .build(),
            "'organization' cannot be null!");
    }

    @Test
    public void shouldAddInvitationParameter() {
        String authUrl = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
            .withInvitation("invitation_123")
            .build();
        assertThat(authUrl, hasQueryParameter("invitation", "invitation_123"));
    }

    @Test
    public void shouldThrowWhenInvitationIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withInvitation(null)
                .build(),
            "'invitation' cannot be null!");
    }

    @Test
    public void shouldAddCodeChallengeParameter() {
        String authUrl = AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
            .withCodeChallenge("insecure_challenge")
            .build();
        assertThat(authUrl, hasQueryParameter("code_challenge", "insecure_challenge"));
        assertThat(authUrl, hasQueryParameter("code_challenge_method", "S256"));
    }

    @Test
    public void shouldThrowWhenChallengeIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> AuthorizeUrlBuilder.newInstance(DOMAIN, CLIENT_ID, REDIRECT_URI)
                .withCodeChallenge(null)
                .build(),
            "'challenge' cannot be null!");
    }
}
