package com.auth0.client.mgmt;

import com.auth0.client.HttpOptions;
import com.auth0.client.MockServer;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.Auth0HttpRequest;
import com.auth0.net.client.Auth0HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static com.auth0.client.UrlMatcher.isUrl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;

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
        api = ManagementAPI.newBuilder(server.getBaseUrl(), API_TOKEN).build();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    // Configuration

    @Test
    public void shouldAcceptDomainWithNoScheme() {
        ManagementAPI api = ManagementAPI.newBuilder("me.something.com", API_TOKEN).build();

        assertThat(api.getBaseUrl(), is(notNullValue()));
        assertThat(api.getBaseUrl().toString(), isUrl("https", "me.something.com"));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void shouldCreateWithDomainAndToken() {
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN);
        assertThat(api, is(notNullValue()));
    }

    @Test
    public void shouldCreateWithHttpClient() {
        Auth0HttpClient httpClient = new Auth0HttpClient() {
            @Override
            public Auth0HttpResponse sendRequest(Auth0HttpRequest request) throws IOException {
                return null;
            }

            @Override
            public CompletableFuture<Auth0HttpResponse> sendRequestAsync(Auth0HttpRequest request) {
                return null;
            }
        };

        ManagementAPI api = ManagementAPI.newBuilder(DOMAIN, API_TOKEN)
            .withHttpClient(httpClient).build();
        assertThat(api, is(notNullValue()));
        assertThat(api.getHttpClient(), is(httpClient));
    }

    @Test
    public void shouldAcceptDomainWithHttpScheme() {
        ManagementAPI api = ManagementAPI.newBuilder("http://me.something.com", API_TOKEN).build();

        assertThat(api.getBaseUrl(), is(notNullValue()));
        assertThat(api.getBaseUrl().toString(), isUrl("http", "me.something.com"));
    }

    @Test
    public void shouldThrowWhenDomainIsInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The domain had an invalid format and couldn't be parsed as an URL.");
        ManagementAPI.newBuilder("", API_TOKEN).build();
    }

    @Test
    public void shouldThrowWhenDomainIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' cannot be null!");
        ManagementAPI.newBuilder(null, API_TOKEN).build();
    }

    @Test
    public void shouldThrowWhenApiTokenIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'api token' cannot be null!");
        ManagementAPI.newBuilder(DOMAIN, null).build();
    }

    @Test
    public void shouldThrowOnUpdateWhenApiTokenIsNull() {
        ManagementAPI api = ManagementAPI.newBuilder(DOMAIN, API_TOKEN).build();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'api token' cannot be null!");
        api.setApiToken(null);
    }

    @Test
    public void shouldUpdateApiToken() {
        //Initialize with a token
        ManagementAPI api = ManagementAPI.newBuilder(DOMAIN, "first token").build();

        assertThat(api.blacklists().apiToken, is("first token"));
        assertThat(api.clientGrants().apiToken, is("first token"));
        assertThat(api.clients().apiToken, is("first token"));
        assertThat(api.connections().apiToken, is("first token"));
        assertThat(api.deviceCredentials().apiToken, is("first token"));
        assertThat(api.emailProvider().apiToken, is("first token"));
        assertThat(api.emailTemplates().apiToken, is("first token"));
        assertThat(api.grants().apiToken, is("first token"));
        assertThat(api.guardian().apiToken, is("first token"));
        assertThat(api.jobs().apiToken, is("first token"));
        assertThat(api.logEvents().apiToken, is("first token"));
        assertThat(api.resourceServers().apiToken, is("first token"));
        assertThat(api.rules().apiToken, is("first token"));
        assertThat(api.stats().apiToken, is("first token"));
        assertThat(api.tenants().apiToken, is("first token"));
        assertThat(api.tickets().apiToken, is("first token"));
        assertThat(api.userBlocks().apiToken, is("first token"));
        assertThat(api.users().apiToken, is("first token"));

        //Update the token
        api.setApiToken("new token");

        assertThat(api.blacklists().apiToken, is("new token"));
        assertThat(api.clientGrants().apiToken, is("new token"));
        assertThat(api.clients().apiToken, is("new token"));
        assertThat(api.connections().apiToken, is("new token"));
        assertThat(api.deviceCredentials().apiToken, is("new token"));
        assertThat(api.emailProvider().apiToken, is("new token"));
        assertThat(api.emailTemplates().apiToken, is("new token"));
        assertThat(api.grants().apiToken, is("new token"));
        assertThat(api.guardian().apiToken, is("new token"));
        assertThat(api.jobs().apiToken, is("new token"));
        assertThat(api.logEvents().apiToken, is("new token"));
        assertThat(api.resourceServers().apiToken, is("new token"));
        assertThat(api.rules().apiToken, is("new token"));
        assertThat(api.stats().apiToken, is("new token"));
        assertThat(api.tenants().apiToken, is("new token"));
        assertThat(api.tickets().apiToken, is("new token"));
        assertThat(api.userBlocks().apiToken, is("new token"));
        assertThat(api.users().apiToken, is("new token"));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void acceptsHttpOptions() {
        HttpOptions httpOptions = new HttpOptions();
        httpOptions.setConnectTimeout(15);
        ManagementAPI api = new ManagementAPI(DOMAIN, "CLIENT_ID", httpOptions);
        assertThat(api, is(notNullValue()));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void httpOptionsShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> new ManagementAPI(DOMAIN, API_TOKEN, null));
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
