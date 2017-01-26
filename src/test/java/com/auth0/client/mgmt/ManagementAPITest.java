package com.auth0.client.mgmt;

import com.auth0.client.MockServer;
import com.auth0.net.TelemetryInterceptor;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.auth0.client.UrlMatcher.isUrl;
import static okhttp3.logging.HttpLoggingInterceptor.Level;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ManagementAPITest {

    private static final String DOMAIN = "domain.auth0.com";
    private static final String API_TOKEN = "apiToken";

    private MockServer server;
    private ManagementAPI api;
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
    public void shouldAcceptDomainWithNoScheme() throws Exception {
        ManagementAPI api = new ManagementAPI("me.something.com", API_TOKEN);

        assertThat(api.getBaseUrl(), isUrl("https", "me.something.com"));
    }

    @Test
    public void shouldAcceptDomainWithHttpScheme() throws Exception {
        ManagementAPI api = new ManagementAPI("http://me.something.com", API_TOKEN);

        assertThat(api.getBaseUrl(), isUrl("http", "me.something.com"));
    }

    @Test
    public void shouldThrowWhenDomainIsInvalid() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The domain had an invalid format and couldn't be parsed as an URL.");
        new ManagementAPI("", API_TOKEN);
    }

    @Test
    public void shouldThrowWhenDomainIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'domain' cannot be null!");
        new ManagementAPI(null, API_TOKEN);
    }

    @Test
    public void shouldThrowWhenApiTokenIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'api token' cannot be null!");
        new ManagementAPI(DOMAIN, null);
    }

    @Test
    public void shouldAddAndEnableTelemetryInterceptor() throws Exception {
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN);
        assertThat(api.getClient().interceptors(), hasItem(isA(TelemetryInterceptor.class)));

        for (Interceptor i : api.getClient().interceptors()) {
            if (i instanceof TelemetryInterceptor) {
                TelemetryInterceptor telemetry = (TelemetryInterceptor) i;
                assertThat(telemetry.isEnabled(), is(true));
            }
        }
    }

    @Test
    public void shouldDisableTelemetryInterceptor() throws Exception {
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN);
        assertThat(api.getClient().interceptors(), hasItem(isA(TelemetryInterceptor.class)));
        api.doNotSendTelemetry();

        for (Interceptor i : api.getClient().interceptors()) {
            if (i instanceof TelemetryInterceptor) {
                TelemetryInterceptor telemetry = (TelemetryInterceptor) i;
                assertThat(telemetry.isEnabled(), is(false));
            }
        }
    }

    @Test
    public void shouldAddAndDisableLoggingInterceptor() throws Exception {
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN);
        assertThat(api.getClient().interceptors(), hasItem(isA(HttpLoggingInterceptor.class)));

        for (Interceptor i : api.getClient().interceptors()) {
            if (i instanceof HttpLoggingInterceptor) {
                HttpLoggingInterceptor logging = (HttpLoggingInterceptor) i;
                assertThat(logging.getLevel(), is(Level.NONE));
            }
        }
    }

    @Test
    public void shouldEnableLoggingInterceptor() throws Exception {
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN);
        assertThat(api.getClient().interceptors(), hasItem(isA(HttpLoggingInterceptor.class)));
        api.setLoggingEnabled(true);

        for (Interceptor i : api.getClient().interceptors()) {
            if (i instanceof HttpLoggingInterceptor) {
                HttpLoggingInterceptor logging = (HttpLoggingInterceptor) i;
                assertThat(logging.getLevel(), is(Level.BODY));
            }
        }
    }

    @Test
    public void shouldDisableLoggingInterceptor() throws Exception {
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN);
        assertThat(api.getClient().interceptors(), hasItem(isA(HttpLoggingInterceptor.class)));
        api.setLoggingEnabled(false);

        for (Interceptor i : api.getClient().interceptors()) {
            if (i instanceof HttpLoggingInterceptor) {
                HttpLoggingInterceptor logging = (HttpLoggingInterceptor) i;
                assertThat(logging.getLevel(), is(Level.NONE));
            }
        }
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