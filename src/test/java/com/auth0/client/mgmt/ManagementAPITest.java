package com.auth0.client.mgmt;

import com.auth0.client.HttpOptions;
import com.auth0.client.MockServer;
import com.auth0.client.ProxyOptions;
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

import static com.auth0.client.UrlMatcher.isUrl;
import static okhttp3.logging.HttpLoggingInterceptor.Level;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

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

//    @Test
//    public void shouldUpdateApiToken() {
//        //Initialize with a token
//        ManagementAPI api = new ManagementAPI(DOMAIN, "first token");
//
//        assertThat(api.blacklists().apiToken, is("first token"));
//        assertThat(api.clientGrants().apiToken, is("first token"));
//        assertThat(api.clients().apiToken, is("first token"));
//        assertThat(api.connections().apiToken, is("first token"));
//        assertThat(api.deviceCredentials().apiToken, is("first token"));
//        assertThat(api.emailProvider().apiToken, is("first token"));
//        assertThat(api.emailTemplates().apiToken, is("first token"));
//        assertThat(api.grants().apiToken, is("first token"));
//        assertThat(api.guardian().apiToken, is("first token"));
//        assertThat(api.jobs().apiToken, is("first token"));
//        assertThat(api.logEvents().apiToken, is("first token"));
//        assertThat(api.resourceServers().apiToken, is("first token"));
//        assertThat(api.rules().apiToken, is("first token"));
//        assertThat(api.stats().apiToken, is("first token"));
//        assertThat(api.tenants().apiToken, is("first token"));
//        assertThat(api.tickets().apiToken, is("first token"));
//        assertThat(api.userBlocks().apiToken, is("first token"));
//        assertThat(api.users().apiToken, is("first token"));
//
//        //Update the token
//        api.setApiToken("new token");
//
//        assertThat(api.blacklists().apiToken, is("new token"));
//        assertThat(api.clientGrants().apiToken, is("new token"));
//        assertThat(api.clients().apiToken, is("new token"));
//        assertThat(api.connections().apiToken, is("new token"));
//        assertThat(api.deviceCredentials().apiToken, is("new token"));
//        assertThat(api.emailProvider().apiToken, is("new token"));
//        assertThat(api.emailTemplates().apiToken, is("new token"));
//        assertThat(api.grants().apiToken, is("new token"));
//        assertThat(api.guardian().apiToken, is("new token"));
//        assertThat(api.jobs().apiToken, is("new token"));
//        assertThat(api.logEvents().apiToken, is("new token"));
//        assertThat(api.resourceServers().apiToken, is("new token"));
//        assertThat(api.rules().apiToken, is("new token"));
//        assertThat(api.stats().apiToken, is("new token"));
//        assertThat(api.tenants().apiToken, is("new token"));
//        assertThat(api.tickets().apiToken, is("new token"));
//        assertThat(api.userBlocks().apiToken, is("new token"));
//        assertThat(api.users().apiToken, is("new token"));
//    }

    @Test
    public void shouldUseDefaultTimeoutIfNotSpecified() {
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN);
        assertThat(api.getClient().connectTimeoutMillis(), is(10 * 1000));
        assertThat(api.getClient().readTimeoutMillis(), is(10 * 1000));
    }

    @Test
    public void shouldUseZeroIfNegativeTimeoutConfigured() {
        HttpOptions options = new HttpOptions();
        options.setConnectTimeout(-1);
        options.setReadTimeout(-1);
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN, options);
        assertThat(api.getClient().connectTimeoutMillis(), is(0));
        assertThat(api.getClient().readTimeoutMillis(), is(0));

    }

    @Test
    public void shouldSetTimeoutsIfConfigured() {
        HttpOptions options = new HttpOptions();
        options.setConnectTimeout(20);
        options.setReadTimeout(30);
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN, options);
        assertThat(api.getClient().connectTimeoutMillis(), is(20 * 1000));
        assertThat(api.getClient().readTimeoutMillis(), is(30 * 1000));
    }

    @Test
    public void shouldNotUseProxyByDefault() throws Exception {
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN);
        assertThat(api.getClient().proxy(), is(nullValue()));
        Authenticator authenticator = api.getClient().proxyAuthenticator();
        assertThat(authenticator, is(notNullValue()));

        Route route = Mockito.mock(Route.class);
        okhttp3.Request nonAuthenticatedRequest = new okhttp3.Request.Builder()
                .url("https://test.com/app")
                .addHeader("some-header", "some-value")
                .build();
        okhttp3.Response nonAuthenticatedResponse = new okhttp3.Response.Builder()
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("OK")
                .request(nonAuthenticatedRequest)
                .build();

        okhttp3.Request processedRequest = authenticator.authenticate(route, nonAuthenticatedResponse);
        assertThat(processedRequest, is(nullValue()));
    }

    @Test
    public void shouldUseProxy() throws Exception {
        Proxy proxy = Mockito.mock(Proxy.class);
        ProxyOptions proxyOptions = new ProxyOptions(proxy);
        HttpOptions httpOptions = new HttpOptions();
        httpOptions.setProxyOptions(proxyOptions);

        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN, httpOptions);
        assertThat(api.getClient().proxy(), is(proxy));
        Authenticator authenticator = api.getClient().proxyAuthenticator();
        assertThat(authenticator, is(notNullValue()));

        Route route = Mockito.mock(Route.class);
        okhttp3.Request nonAuthenticatedRequest = new okhttp3.Request.Builder()
                .url("https://test.com/app")
                .addHeader("some-header", "some-value")
                .build();
        okhttp3.Response nonAuthenticatedResponse = new okhttp3.Response.Builder()
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("OK")
                .request(nonAuthenticatedRequest)
                .build();

        okhttp3.Request processedRequest = authenticator.authenticate(route, nonAuthenticatedResponse);

        assertThat(processedRequest, is(nullValue()));
    }

    @Test
    public void shouldUseProxyWithAuthentication() throws Exception {
        Proxy proxy = Mockito.mock(Proxy.class);
        ProxyOptions proxyOptions = new ProxyOptions(proxy);
        proxyOptions.setBasicAuthentication("johndoe", "psswd".toCharArray());
        assertThat(proxyOptions.getBasicAuthentication(), is("Basic am9obmRvZTpwc3N3ZA=="));
        HttpOptions httpOptions = new HttpOptions();
        httpOptions.setProxyOptions(proxyOptions);

        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN, httpOptions);
        assertThat(api.getClient().proxy(), is(proxy));
        Authenticator authenticator = api.getClient().proxyAuthenticator();
        assertThat(authenticator, is(notNullValue()));

        Route route = Mockito.mock(Route.class);
        okhttp3.Request nonAuthenticatedRequest = new okhttp3.Request.Builder()
                .url("https://test.com/app")
                .addHeader("some-header", "some-value")
                .build();
        okhttp3.Response nonAuthenticatedResponse = new okhttp3.Response.Builder()
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("OK")
                .request(nonAuthenticatedRequest)
                .build();

        okhttp3.Request processedRequest = authenticator.authenticate(route, nonAuthenticatedResponse);

        assertThat(processedRequest, is(notNullValue()));
        assertThat(processedRequest.url(), is(HttpUrl.parse("https://test.com/app")));
        assertThat(processedRequest.header("Proxy-Authorization"), is(proxyOptions.getBasicAuthentication()));
        assertThat(processedRequest.header("some-header"), is("some-value"));
    }

    @Test
    public void proxyShouldNotProcessAlreadyAuthenticatedRequest() throws Exception {
        Proxy proxy = Mockito.mock(Proxy.class);
        ProxyOptions proxyOptions = new ProxyOptions(proxy);
        proxyOptions.setBasicAuthentication("johndoe", "psswd".toCharArray());
        assertThat(proxyOptions.getBasicAuthentication(), is("Basic am9obmRvZTpwc3N3ZA=="));
        HttpOptions httpOptions = new HttpOptions();
        httpOptions.setProxyOptions(proxyOptions);

        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN, httpOptions);
        assertThat(api.getClient().proxy(), is(proxy));
        Authenticator authenticator = api.getClient().proxyAuthenticator();
        assertThat(authenticator, is(notNullValue()));

        Route route = Mockito.mock(Route.class);
        okhttp3.Request alreadyAuthenticatedRequest = new okhttp3.Request.Builder()
                .url("https://test.com/app")
                .addHeader("some-header", "some-value")
                .header("Proxy-Authorization", "pre-existing-value")
                .build();
        okhttp3.Response alreadyAuthenticatedResponse = new okhttp3.Response.Builder()
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("OK")
                .request(alreadyAuthenticatedRequest)
                .build();

        okhttp3.Request processedRequest = authenticator.authenticate(route, alreadyAuthenticatedResponse);
        assertThat(processedRequest, is(nullValue()));
    }

    @Test
    public void shouldAddAndEnableTelemetryInterceptor() {
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
    public void shouldUseCustomTelemetry() {
        ManagementAPI api = new ManagementAPI(DOMAIN, API_TOKEN);
        assertThat(api.getClient().interceptors(), hasItem(isA(TelemetryInterceptor.class)));

        Telemetry currentTelemetry = null;
        for (Interceptor i : api.getClient().interceptors()) {
            if (i instanceof TelemetryInterceptor) {
                TelemetryInterceptor interceptor = (TelemetryInterceptor) i;
                currentTelemetry = interceptor.getTelemetry();
            }
        }
        assertThat(currentTelemetry, is(notNullValue()));

        Telemetry newTelemetry = Mockito.mock(Telemetry.class);
        api.setTelemetry(newTelemetry);

        Telemetry updatedTelemetry = null;
        for (Interceptor i : api.getClient().interceptors()) {
            if (i instanceof TelemetryInterceptor) {
                TelemetryInterceptor interceptor = (TelemetryInterceptor) i;
                updatedTelemetry = interceptor.getTelemetry();
            }
        }
        assertThat(updatedTelemetry, is(newTelemetry));
    }

    @Test
    public void shouldDisableTelemetryInterceptor() {
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
    public void shouldAddAndDisableLoggingInterceptor() {
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
    public void shouldEnableLoggingInterceptor() {
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
    public void shouldDisableLoggingInterceptor() {
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
