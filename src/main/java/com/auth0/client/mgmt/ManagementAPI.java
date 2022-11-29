package com.auth0.client.mgmt;

import com.auth0.client.HttpOptions;
import com.auth0.client.LoggingOptions;
import com.auth0.net.Telemetry;
import com.auth0.net.TelemetryInterceptor;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.DefaultHttpClient;
import com.auth0.utils.Asserts;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.jetbrains.annotations.TestOnly;

/**
 * Class that provides an implementation of the Management API methods defined in https://auth0.com/docs/api/management/v2.
 * To begin create an instance of {@link #ManagementAPI(String, String)} using the tenant domain and API token.
 * <p>
 * This class is not entirely thread-safe:
 * A new immutable {@link OkHttpClient} instance is being created with each instantiation, not sharing the thread pool
 * with any prior existing client instance.
 */
@SuppressWarnings("WeakerAccess")
public class ManagementAPI {

    private final HttpUrl baseUrl;
    private String apiToken;
    private final Auth0HttpClient client;

    /**
     * Create an instance with the given tenant's domain and API token.
     * In addition, accepts an {@link HttpOptions} that will be used to configure the networking client.
     * See the Management API section in the readme or visit https://auth0.com/docs/api/management/v2/tokens
     * to learn how to obtain a token.
     *
     * @param domain   the tenant's domain.
     * @param apiToken the token to authenticate the calls with.
     * @param options  configuration options for this client instance.
     * @see #ManagementAPI(String, String)
     */
    // TODO deprecate and provide Builder
    public ManagementAPI(String domain, String apiToken, HttpOptions options) {
        this(domain, apiToken, buildNetworkingClient(options));
    }

    /**
     * Create an instance with the given tenant's domain and API token.
     * See the Management API section in the readme or visit https://auth0.com/docs/api/management/v2/tokens
     * to learn how to obtain a token.
     *
     * @param domain   the tenant's domain.
     * @param apiToken the token to authenticate the calls with.
     */
    // TODO deprecate and provide Builder
    public ManagementAPI(String domain, String apiToken) {
        this(domain, apiToken, DefaultHttpClient.newBuilder().build());
    }

    /**
     * Instantiate a new {@link Builder} to configure and build a new ManagementAPI client.
     *
     * @param domain the tenant's domain. Must be a non-null valid HTTPS domain.
     * @param apiToken the token to use when making API requests to the Auth0 Management API.
     * @return a Builder for further configuration.
     */
    public static ManagementAPI.Builder newBuilder(String domain, String apiToken) {
        return new ManagementAPI.Builder(domain, apiToken);
    }

    private ManagementAPI(String domain, String apiToken, Auth0HttpClient httpClient) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(apiToken, "api token");

        this.baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.apiToken = apiToken;
        this.client = httpClient;
    }

    /**
     * Given a set of options, it creates a new instance of the {@link OkHttpClient}
     * configuring them according to their availability.
     *
     * @param options the options to set to the client.
     * @return a new networking client instance configured as requested.
     */
    private static DefaultHttpClient buildNetworkingClient(HttpOptions options) {
        Asserts.assertNotNull(options, "Http options");
        return DefaultHttpClient.newBuilder()
            .withLogging(options.getLoggingOptions())
            .withMaxRetries(options.getManagementAPIMaxRetries())
            .withMaxRequests(options.getMaxRequests())
            .withMaxRequestsPerHost(options.getMaxRequestsPerHost())
            .withProxy(options.getProxyOptions())
            .withConnectTimeout(options.getConnectTimeout())
            .withReadTimeout(options.getReadTimeout())
            .build();
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

    @TestOnly
    Auth0HttpClient getHttpClient() {
        return this.client;
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
     * Getter for the Branding entity.
     *
     * @return the Branding entity.
     */
    public BrandingEntity branding() {
        return new BrandingEntity(client, baseUrl, apiToken);
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
     * Getter for the Log Streams entity.
     *
     * @return the Log Streams entity.
     */
    public LogStreamsEntity logStreams() {
        return new LogStreamsEntity(client, baseUrl, apiToken);
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
     * Getter for the Rules Configs entity.
     *
     * @return the Rules Configs entity.
     */
    public RulesConfigsEntity rulesConfigs() {
        return new RulesConfigsEntity(client, baseUrl, apiToken);
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

    /**
     * Getter for the Roles entity.
     *
     * @return the Roles entity.
     */
    public RolesEntity roles() {
        return new RolesEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Organizations entity.
     *
     * @return the Organizations entity.
     */
    public OrganizationsEntity organizations() {
        return new OrganizationsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Actions entity.
     *
     * @return the Actions entity.
     */
    public ActionsEntity actions() {
        return new ActionsEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Attack Protection Entity
     *
     * @return the Attack Protection Entity
     */
    public AttackProtectionEntity attackProtection() {
        return new AttackProtectionEntity(client, baseUrl, apiToken);
    }

    /**
     * Getter for the Keys Entity
     *
     * @return the Keys Entity
     */
    public KeysEntity keys() {
        return new KeysEntity(client, baseUrl, apiToken);
    }

    /**
     * Builder for {@link ManagementAPI} API client instances.
     */
    public static class Builder {
        private final String domain;
        private final String apiToken;
        private Auth0HttpClient httpClient = DefaultHttpClient.newBuilder().build();

        /**
         * Create a new Builder
         * @param domain the domain of the tenant.
         * @param apiToken the API token used to make requests to the Auth0 Management API.
         */
        public Builder(String domain, String apiToken) {
            this.domain = domain;
            this.apiToken = apiToken;
        }

        /**
         * Configure the client with an {@link Auth0HttpClient}.
         * @param httpClient the HTTP client to use when making requests.
         * @return the builder instance.
         * @see DefaultHttpClient
         */
        public Builder withHttpClient(Auth0HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Build a {@link ManagementAPI} instance using this builder's configuration.
         * @return the configured {@code ManagementAPI} instance.
         */
        public ManagementAPI build() {
            return new ManagementAPI(domain, apiToken, httpClient);
        }
    }
}
