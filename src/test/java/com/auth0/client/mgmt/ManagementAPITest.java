package com.auth0.client.mgmt;

import com.auth0.client.HttpOptions;
import com.auth0.client.LoggingOptions;
import com.auth0.client.MockServer;
import com.auth0.client.ProxyOptions;
import com.auth0.exception.Auth0Exception;
import com.auth0.net.RateLimitInterceptor;
import com.auth0.net.Telemetry;
import com.auth0.net.TelemetryInterceptor;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.net.Proxy;
import java.util.HashSet;
import java.util.Set;

import static com.auth0.client.UrlMatcher.isUrl;
import static okhttp3.logging.HttpLoggingInterceptor.Level;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ManagementAPITest {

    private static final String DOMAIN = "domain.auth0.com";
    private static final String API_TOKEN = "apiToken";

    private MockServer server;
    private ManagementAPI api;

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        api = new ManagementAPI(server.getBaseUrl(), API_TOKEN);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    // Configuration

    @Test
    public void shouldAcceptDomainWithNoScheme() {
        ManagementAPI api = new ManagementAPI("me.something.com", API_TOKEN);

        assertThat(api.getBaseUrl(), is(notNullValue()));
        assertThat(api.getBaseUrl().toString(), isUrl("https", "me.something.com"));
    }

    @Test
    public void shouldAcceptDomainWithHttpScheme() {
        ManagementAPI api = new ManagementAPI("http://me.something.com", API_TOKEN);

        assertThat(api.getBaseUrl(), is(notNullValue()));
        assertThat(api.getBaseUrl().toString(), isUrl("http", "me.something.com"));
    }

    @Test
    public void shouldThrowWhenDomainIsInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The domain had an invalid format and couldn't be parsed as an URL.");
        new ManagementAPI("", API_TOKEN);
    }

    @Test
    public void shouldThrowWhenDomainIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' cannot be null!");
        new ManagementAPI(null, API_TOKEN);
    }

    @Test
    public void shouldThrowWhenApiTokenIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'api token' cannot be null!");
        new ManagementAPI(DOMAIN, null);
    }

    @Test
    public void shouldThrowOnUpdateWhenApiTokenIsNull() {
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'api token' cannot be null!");
        api.setApiToken(null);
    }

    @Test
    public void shouldUpdateApiToken() throws Auth0Exception {
        //Initialize with a token
        ManagementAPI api = new ManagementAPI(DOMAIN, "first token");

        assertThat(api.blacklists().tokenProvider.getToken(), is("first token"));
        assertThat(api.clientGrants().tokenProvider.getToken(), is("first token"));
        assertThat(api.clients().tokenProvider.getToken(), is("first token"));
        assertThat(api.connections().tokenProvider.getToken(), is("first token"));
        assertThat(api.deviceCredentials().tokenProvider.getToken(), is("first token"));
        assertThat(api.emailProvider().tokenProvider.getToken(), is("first token"));
        assertThat(api.emailTemplates().tokenProvider.getToken(), is("first token"));
        assertThat(api.grants().tokenProvider.getToken(), is("first token"));
        assertThat(api.guardian().tokenProvider.getToken(), is("first token"));
        assertThat(api.jobs().tokenProvider.getToken(), is("first token"));
        assertThat(api.logEvents().tokenProvider.getToken(), is("first token"));
        assertThat(api.resourceServers().tokenProvider.getToken(), is("first token"));
        assertThat(api.rules().tokenProvider.getToken(), is("first token"));
        assertThat(api.stats().tokenProvider.getToken(), is("first token"));
        assertThat(api.tenants().tokenProvider.getToken(), is("first token"));
        assertThat(api.tickets().tokenProvider.getToken(), is("first token"));
        assertThat(api.userBlocks().tokenProvider.getToken(), is("first token"));
        assertThat(api.users().tokenProvider.getToken(), is("first token"));

        //Update the token
        api.setApiToken("new token");

        assertThat(api.blacklists().tokenProvider.getToken(), is("new token"));
        assertThat(api.clientGrants().tokenProvider.getToken(), is("new token"));
        assertThat(api.clients().tokenProvider.getToken(), is("new token"));
        assertThat(api.connections().tokenProvider.getToken(), is("new token"));
        assertThat(api.deviceCredentials().tokenProvider.getToken(), is("new token"));
        assertThat(api.emailProvider().tokenProvider.getToken(), is("new token"));
        assertThat(api.emailTemplates().tokenProvider.getToken(), is("new token"));
        assertThat(api.grants().tokenProvider.getToken(), is("new token"));
        assertThat(api.guardian().tokenProvider.getToken(), is("new token"));
        assertThat(api.jobs().tokenProvider.getToken(), is("new token"));
        assertThat(api.logEvents().tokenProvider.getToken(), is("new token"));
        assertThat(api.resourceServers().tokenProvider.getToken(), is("new token"));
        assertThat(api.rules().tokenProvider.getToken(), is("new token"));
        assertThat(api.stats().tokenProvider.getToken(), is("new token"));
        assertThat(api.tenants().tokenProvider.getToken(), is("new token"));
        assertThat(api.tickets().tokenProvider.getToken(), is("new token"));
        assertThat(api.userBlocks().tokenProvider.getToken(), is("new token"));
        assertThat(api.users().tokenProvider.getToken(), is("new token"));
    }

    @Test
    public void shouldThrowOnInValidMaxRequestsPerHostConfiguration() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("maxRequestsPerHost must be one or greater.");

        HttpOptions options = new HttpOptions();
        options.setMaxRequestsPerHost(0);
    }

    //Entities

    @Test
    public void shouldGetBlacklists() {
        assertThat(api.blacklists(), notNullValue());
    }

    @Test
    public void shouldGetClientGrants() {
        assertThat(api.clientGrants(), notNullValue());
    }

    @Test
    public void shouldGetClients() {
        assertThat(api.clients(), notNullValue());
    }

    @Test
    public void shouldGetConnections() {
        assertThat(api.connections(), notNullValue());
    }

    @Test
    public void shouldGetDeviceCredentials() {
        assertThat(api.deviceCredentials(), notNullValue());
    }

    @Test
    public void shouldGetEmailProvider() {
        assertThat(api.emailProvider(), notNullValue());
    }

    @Test
    public void shouldGetEmailTemplates() {
        assertThat(api.emailTemplates(), notNullValue());
    }

    @Test
    public void shouldGetGrants() {
        assertThat(api.grants(), notNullValue());
    }

    @Test
    public void shouldGetGuardian() {
        assertThat(api.guardian(), notNullValue());
    }

    @Test
    public void shouldGetJobs() {
        assertThat(api.jobs(), notNullValue());
    }

    @Test
    public void shouldGetLogEvents() {
        assertThat(api.logEvents(), notNullValue());
    }

    @Test
    public void shouldGetResourceServers() {
        assertThat(api.resourceServers(), notNullValue());
    }

    @Test
    public void shouldGetRules() {
        assertThat(api.rules(), notNullValue());
    }

    @Test
    public void shouldGetStats() {
        assertThat(api.stats(), notNullValue());
    }

    @Test
    public void shouldGetTenants() {
        assertThat(api.tenants(), notNullValue());
    }

    @Test
    public void shouldGetTickets() {
        assertThat(api.tickets(), notNullValue());
    }

    @Test
    public void shouldGetUserBlocks() {
        assertThat(api.userBlocks(), notNullValue());
    }

    @Test
    public void shouldGetUsers() {
        assertThat(api.users(), notNullValue());
    }

}
