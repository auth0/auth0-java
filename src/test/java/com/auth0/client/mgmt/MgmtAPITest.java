package com.auth0.client.mgmt;

import com.auth0.client.MockServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.auth0.client.UrlMatcher.isUrl;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MgmtAPITest {

    private static final String DOMAIN = "domain.auth0.com";
    private static final String API_TOKEN = "apiToken";

    private MockServer server;
    private MgmtAPI api;
    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        api = new MgmtAPI(server.getBaseUrl(), API_TOKEN);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    // Configuration

    @Test
    public void shouldAcceptDomainWithNoScheme() throws Exception {
        MgmtAPI api = new MgmtAPI("me.something.com", API_TOKEN);

        assertThat(api.getBaseUrl(), isUrl("https", "me.something.com"));
    }

    @Test
    public void shouldAcceptDomainWithHttpScheme() throws Exception {
        MgmtAPI api = new MgmtAPI("http://me.something.com", API_TOKEN);

        assertThat(api.getBaseUrl(), isUrl("http", "me.something.com"));
    }

    @Test
    public void shouldThrowWhenDomainIsInvalid() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The domain had an invalid format and couldn't be parsed as an URL.");
        new MgmtAPI("", API_TOKEN);
    }

    @Test
    public void shouldThrowWhenDomainIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' cannot be null!");
        new MgmtAPI(null, API_TOKEN);
    }

    @Test
    public void shouldThrowWhenApiTokenIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'api token' cannot be null!");
        new MgmtAPI(DOMAIN, null);
    }

    //Entities

    @Test
    public void shouldGetClientGrants() throws Exception {
        assertThat(api.clientGrants(), notNullValue());
    }

    @Test
    public void shouldGetClients() throws Exception {
        assertThat(api.clients(), notNullValue());
    }

    @Test
    public void shouldGetConnections() throws Exception {
        assertThat(api.connections(), notNullValue());
    }

    @Test
    public void shouldGetDeviceCredentials() throws Exception {
        assertThat(api.deviceCredentials(), notNullValue());
    }

    @Test
    public void shouldGetLogEvents() throws Exception {
        assertThat(api.logEvents(), notNullValue());
    }

    @Test
    public void shouldGetResourceServers() throws Exception {
        assertThat(api.resourceServers(), notNullValue());
    }

    @Test
    public void shouldGetRules() throws Exception {
        assertThat(api.rules(), notNullValue());
    }

    @Test
    public void shouldGetUserBlocks() throws Exception {
        assertThat(api.userBlocks(), notNullValue());
    }

    @Test
    public void shouldGetUsers() throws Exception {
        assertThat(api.users(), notNullValue());
    }

    @Test
    public void shouldGetBlacklists() throws Exception {
        assertThat(api.blacklists(), notNullValue());
    }

    @Test
    public void shouldGetEmailProvider() throws Exception {
        assertThat(api.emailProvider(), notNullValue());
    }

    @Test
    public void shouldGetGuardian() throws Exception {
        assertThat(api.guardian(), notNullValue());
    }

    @Test
    public void shouldGetStats() throws Exception {
        assertThat(api.stats(), notNullValue());
    }

    @Test
    public void shouldGetTenants() throws Exception {
        assertThat(api.tenants(), notNullValue());
    }

    @Test
    public void shouldGetTickets() throws Exception {
        assertThat(api.tickets(), notNullValue());
    }
}