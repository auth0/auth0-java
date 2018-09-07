package com.auth0.client.mgmt;

import com.auth0.net.TelemetryInterceptor;
import com.auth0.utils.Asserts;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

import java.net.PasswordAuthentication;
import java.net.Proxy;

/**
 * Class that provides an implementation of the Management API methods defined in https://auth0.com/docs/api/management/v2.
 * To begin create an instance of {@link #ManagementAPI(String, String)} using the tenant domain and API token.
 */
@SuppressWarnings("WeakerAccess")
public class ManagementAPI {

    private final HttpUrl baseUrl;
    private String apiToken;
    private final OkHttpClient client;
    private final TelemetryInterceptor telemetry;
    private final HttpLoggingInterceptor logging;

    /**
     * Create an instance with the given tenant's domain and API token.
     * See the Management API section in the readme or visit https://auth0.com/docs/api/management/v2/tokens to learn how to obtain a token.
     *
     * @param domain   the tenant's domain.
     * @param apiToken the token to authenticate the calls with.
     */
    public ManagementAPI(String domain, String apiToken) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(apiToken, "api token");

        this.baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.apiToken = apiToken;

        telemetry = new TelemetryInterceptor();
        logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.NONE);
        this.client = constructOkHttpClient(null, null);
    }

    /**
     * Create an instance with the given tenant's domain and API token.
     * See the Management API section in the readme or visit https://auth0.com/docs/api/management/v2/tokens to learn how to obtain a token.
     *
     * @param domain   the tenant's domain.
     * @param apiToken the token to authenticate the calls with.
     * @param proxy    the {@link Proxy} instance.
     */
    public ManagementAPI(String domain, String apiToken, Proxy proxy) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(apiToken, "api token");

        this.baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.apiToken = apiToken;

        telemetry = new TelemetryInterceptor();
        logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.NONE);
        this.client = constructOkHttpClient(proxy, null);
    }

    /**
     * Create an instance with the given tenant's domain and API token.
     * See the Management API section in the readme or visit https://auth0.com/docs/api/management/v2/tokens to learn how to obtain a token.
     *
     * @param domain                 the tenant's domain.
     * @param apiToken               the token to authenticate the calls with.
     * @param proxy                  the {@link Proxy} instance.
     * @param passwordAuthentication the {@link PasswordAuthentication} instance.
     */
    public ManagementAPI(String domain, String apiToken, Proxy proxy, PasswordAuthentication passwordAuthentication) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(apiToken, "api token");

        this.baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.apiToken = apiToken;

        telemetry = new TelemetryInterceptor();
        logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.NONE);
        this.client = constructOkHttpClient(proxy, passwordAuthentication);
    }

    /**
     * Constructs an instance of {@link OkHttpClient}. Visible for Testing.
     *
     * @param proxy                  the {@link Proxy} instance.
     * @param passwordAuthentication the {@link PasswordAuthentication} instance.
     * @return
     */
    OkHttpClient constructOkHttpClient(Proxy proxy, PasswordAuthentication passwordAuthentication) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(telemetry)
                .proxy(proxy);
        if(passwordAuthentication != null) {
            okHttpClientBuilder.authenticator(constructAuthenticator(passwordAuthentication));
        }
        return okHttpClientBuilder.build();
    }

    /**
     * Constructs an instance of {@link Authenticator}. Visible for Testing.
     *
     * @param passwordAuthentication the {@link PasswordAuthentication} instance.
     * @return
     */
    Authenticator constructAuthenticator(final PasswordAuthentication passwordAuthentication) {
        Asserts.assertNotNull(passwordAuthentication, "password authentication");
        return new Authenticator() {
            @Override
            public okhttp3.Request authenticate(Route route, Response response) {
                if (response.request().header("Authorization") != null) {
                    return null; // Give up, we've already attempted to authenticate.
                }
                String credential = Credentials.basic(passwordAuthentication.getUserName(),
                        new String(passwordAuthentication.getPassword()));
                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            }
        };
    }

    /**
     * Update the API token to use on new calls. This is useful when the token is about to expire or already has.
     * Please note you'll need to obtain the corresponding entity again for this to apply. e.g. call {@link #clients()} again.
     * See the Management API section in the readme or visit https://auth0.com/docs/api/management/v2/tokens to learn how to obtain a token.
     *
     * @param apiToken the token to authenticate the calls with.
     */
    public void setApiToken(String apiToken) {
        Asserts.assertNotNull(apiToken, "api token");
        this.apiToken = apiToken;
    }

    /**
     * Avoid sending Telemetry data in every request to the Auth0 servers.
     */
    public void doNotSendTelemetry() {
        telemetry.setEnabled(false);
    }

    /**
     * Whether to enable or not the current HTTP Logger for every Request, Response and other sensitive information.
     *
     * @param enabled whether to enable the HTTP logger or not.
     */
    public void setLoggingEnabled(boolean enabled) {
        logging.setLevel(enabled ? Level.BODY : Level.NONE);
    }

    //Visible for testing
    OkHttpClient getClient() {
        return client;
    }

    //Visible for testing
    HttpUrl getBaseUrl() {
        return baseUrl;
    }

    private HttpUrl createBaseUrl(String domain) {
        String url = domain;
        if (!domain.startsWith("https://") && !domain.startsWith("http://")) {
            url = "https://" + domain;
        }
        return HttpUrl.parse(url);
    }

    /**
     * Getter for the Client Grants entity.
     *
     * @return the Client Grants entity.
     */
    public ClientGrantsEntity clientGrants() {
        return new ClientGrantsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Applications entity.
     *
     * @return the Applications entity.
     */
    public ClientsEntity clients() {
        return new ClientsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Connections entity.
     *
     * @return the Connections entity.
     */
    public ConnectionsEntity connections() {
        return new ConnectionsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Device Credentials entity.
     *
     * @return the Device Credentials entity.
     */
    public DeviceCredentialsEntity deviceCredentials() {
        return new DeviceCredentialsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Grants entity.
     *
     * @return the Grants entity.
     */
    public GrantsEntity grants() {
        return new GrantsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Log Events entity.
     *
     * @return the Log Events entity.
     */
    public LogEventsEntity logEvents() {
        return new LogEventsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Rules entity.
     *
     * @return the Rules entity.
     */
    public RulesEntity rules() {
        return new RulesEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the User Blocks entity.
     *
     * @return the User Blocks entity.
     */
    public UserBlocksEntity userBlocks() {
        return new UserBlocksEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Users entity.
     *
     * @return the Users entity.
     */
    public UsersEntity users() {
        return new UsersEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Blacklists entity.
     *
     * @return the Blacklists entity.
     */
    public BlacklistsEntity blacklists() {
        return new BlacklistsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Email Templates entity.
     *
     * @return the Email Templates entity.
     */
    public EmailTemplatesEntity emailTemplates() {
        return new EmailTemplatesEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Email Provider entity.
     *
     * @return the Email Provider entity.
     */
    public EmailProviderEntity emailProvider() {
        return new EmailProviderEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Guardian entity.
     *
     * @return the Guardian entity.
     */
    public GuardianEntity guardian() {
        return new GuardianEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Stats entity.
     *
     * @return the Stats entity.
     */
    public StatsEntity stats() {
        return new StatsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Tenants entity.
     *
     * @return the Tenants entity.
     */
    public TenantsEntity tenants() {
        return new TenantsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Tickets entity.
     *
     * @return the Tickets entity.
     */
    public TicketsEntity tickets() {
        return new TicketsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Resource Servers entity.
     *
     * @return the Resource Servers entity.
     */
    public ResourceServerEntity resourceServers() {
        return new ResourceServerEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Jobs entity.
     *
     * @return the Jobs entity.
     */
    public JobsEntity jobs() {
        return new JobsEntity(client, baseUrl, apiToken);
    }
}
