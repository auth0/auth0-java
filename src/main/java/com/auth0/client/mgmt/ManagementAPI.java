package com.auth0.client.mgmt;

import com.auth0.client.HttpOptions;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.DefaultHttpClient;
import com.auth0.utils.Asserts;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.TestOnly;

/**
 * Class that provides an implementation of the Management API methods defined at
 * <a href="https://auth0.com/docs/api/management/v2.">https://auth0.com/docs/api/management/v2.</a>.
 */
@SuppressWarnings("WeakerAccess")
public class ManagementAPI {

    private final HttpUrl baseUrl;
    private final Auth0HttpClient client;

    private TokenProvider tokenProvider;

    /**
     * Create an instance with the given tenant's domain and API token.
     * In addition, accepts an {@link HttpOptions} that will be used to configure the networking client.
     * See the Management API section in the readme or visit https://auth0.com/docs/api/management/v2/tokens
     * to learn how to obtain a token.
     *
     * @deprecated Use the {@link Builder} to configure and create instances.
     *
     * @param domain   the tenant's domain.
     * @param apiToken the token to authenticate the calls with.
     * @param options  configuration options for this client instance.
     * @see #newBuilder(String, String)
     * @see #ManagementAPI(String, String)
     */
    @Deprecated
    public ManagementAPI(String domain, String apiToken, HttpOptions options) {
        this(domain, SimpleTokenProvider.create(apiToken), buildNetworkingClient(options));
    }

    /**
     * Create an instance with the given tenant's domain and API token.
     * See the Management API section in the readme or visit https://auth0.com/docs/api/management/v2/tokens
     * to learn how to obtain a token.
     *
     * @deprecated Use the {@link Builder} to configure and create instances.
     *
     * @param domain   the tenant's domain.
     * @param apiToken the token to authenticate the calls with.
     * @see #newBuilder(String, String)
     */
    @Deprecated
    public ManagementAPI(String domain, String apiToken) {
        this(domain, SimpleTokenProvider.create(apiToken), DefaultHttpClient.newBuilder().build());
    }

    /**
     * Instantiate a new {@link Builder} to configure and build a new ManagementAPI client.
     * <p>
     * This will configure a {@code ManagementAPI} client with a token required to authorize
     * API calls. Once expired, you will need to either instantiate a new API client or
     * call {@link #setApiToken(String)} with a new token. If your usage requires an API
     * client instance that spans the lifetime of the token, consider configuring this client
     * to fetch, manage, and renew the token automatically with {@link #newBuilder(String, TokenProvider)}.
     * </p>
     *
     * <pre>
     * {@code
     * // Obtain token for ManagementAPI
     * AuthAPI auth = AuthAPI.newBuilder("DOMAIN", "CLIENT-ID", "CLIENT-SECRET").build();
     * TokenHolder tokenHolder = auth.requestToken("https://{DOMAIN}/api/v2/").execute().getBody();
     *
     * // Use token to create ManagementAPI
     * ManagementAPI mgmt = ManagementAPI.newBuilder("{DOMAIN}", tokenHolder.getAccessToken()).build();
     * }
     * </pre>
     *
     * @param domain the tenant's domain. Must be a non-null valid HTTPS domain.
     * @param apiToken the token to use when making API requests to the Auth0 Management API.
     * @return a Builder for further configuration.
     * @see #newBuilder(String, String) 
     */
    public static ManagementAPI.Builder newBuilder(String domain, String apiToken) {
        return new ManagementAPI.Builder(domain)
            .withApiToken(apiToken);
    }

    /**
     * Instantiate a new {@link Builder} to configure and build a new Management API client,
     * using a {@link TokenProvider} configured for an Auth0 application
     * <a href="https://auth0.com/docs/secure/tokens/access-tokens/get-management-api-access-tokens-for-production">authorized for the Management API.</a>
     * Using this will construct a client that will fetch a Management API token for you, and renew it as needed.
     * Use this if your usage requires an API client instance to be long-lived, and prefer that this API client fetch,
     * manage, and renew the API token for you.
     * <pre>
     * {@code
     * TokenProvider provider = ManagedTokenProvider.newBuilder("{DOMAIN}", "{CLIENT-ID}", "{CLIENT-SECRET}").build();
     * ManagementAPI mgmt = ManagementAPI.newBuilder("{DOMAIN}", provider).build();
     * }
     * </pre>
     *
     * @param domain the tenant's domain. Must be a non-null valid HTTPS domain.
     * @param tokenProvider a {@code TokenProvider} instance responsible for managing the token.
     * @return a Builder for further configuration.
     * @see #newBuilder(String, String)
     */
    public static ManagementAPI.Builder newBuilder(String domain, TokenProvider tokenProvider) {
        return new ManagementAPI.Builder(domain)
            .withTokenProvider(tokenProvider);
    }
    private ManagementAPI(String domain, TokenProvider tokenProvider, Auth0HttpClient httpClient) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(tokenProvider, "token provider");

        this.baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.tokenProvider = tokenProvider;
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
    public synchronized void setApiToken(String apiToken) {
        Asserts.assertNotNull(apiToken, "api token");
        this.tokenProvider = SimpleTokenProvider.create(apiToken);
    }

    @TestOnly
    Auth0HttpClient getHttpClient() {
        return this.client;
    }

    @TestOnly
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
        return new BrandingEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Client Grants entity.
     *
     * @return the Client Grants entity.
     */
    public ClientGrantsEntity clientGrants() {
        return new ClientGrantsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Applications entity.
     *
     * @return the Applications entity.
     */
    public ClientsEntity clients() {
        return new ClientsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Connections entity.
     *
     * @return the Connections entity.
     */
    public ConnectionsEntity connections() {
        return new ConnectionsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Device Credentials entity.
     *
     * @return the Device Credentials entity.
     */
    public DeviceCredentialsEntity deviceCredentials() {
        return new DeviceCredentialsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Grants entity.
     *
     * @return the Grants entity.
     */
    public GrantsEntity grants() {
        return new GrantsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Log Events entity.
     *
     * @return the Log Events entity.
     */
    public LogEventsEntity logEvents() {
        return new LogEventsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Log Streams entity.
     *
     * @return the Log Streams entity.
     */
    public LogStreamsEntity logStreams() {
        return new LogStreamsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Rules entity.
     *
     * @return the Rules entity.
     */
    public RulesEntity rules() {
        return new RulesEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Rules Configs entity.
     *
     * @return the Rules Configs entity.
     */
    public RulesConfigsEntity rulesConfigs() {
        return new RulesConfigsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the User Blocks entity.
     *
     * @return the User Blocks entity.
     */
    public UserBlocksEntity userBlocks() {
        return new UserBlocksEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Users entity.
     *
     * @return the Users entity.
     */
    public UsersEntity users() {
        return new UsersEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Blacklists entity.
     *
     * @return the Blacklists entity.
     */
    public BlacklistsEntity blacklists() {
        return new BlacklistsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Email Templates entity.
     *
     * @return the Email Templates entity.
     */
    public EmailTemplatesEntity emailTemplates() {
        return new EmailTemplatesEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Email Provider entity.
     *
     * @return the Email Provider entity.
     */
    public EmailProviderEntity emailProvider() {
        return new EmailProviderEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Guardian entity.
     *
     * @return the Guardian entity.
     */
    public GuardianEntity guardian() {
        return new GuardianEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Stats entity.
     *
     * @return the Stats entity.
     */
    public StatsEntity stats() {
        return new StatsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Tenants entity.
     *
     * @return the Tenants entity.
     */
    public TenantsEntity tenants() {
        return new TenantsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Tickets entity.
     *
     * @return the Tickets entity.
     */
    public TicketsEntity tickets() {
        return new TicketsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Resource Servers entity.
     *
     * @return the Resource Servers entity.
     */
    public ResourceServerEntity resourceServers() {
        return new ResourceServerEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Jobs entity.
     *
     * @return the Jobs entity.
     */
    public JobsEntity jobs() {
        return new JobsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Roles entity.
     *
     * @return the Roles entity.
     */
    public RolesEntity roles() {
        return new RolesEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Organizations entity.
     *
     * @return the Organizations entity.
     */
    public OrganizationsEntity organizations() {
        return new OrganizationsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Actions entity.
     *
     * @return the Actions entity.
     */
    public ActionsEntity actions() {
        return new ActionsEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Attack Protection Entity
     *
     * @return the Attack Protection Entity
     */
    public AttackProtectionEntity attackProtection() {
        return new AttackProtectionEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Getter for the Keys Entity
     *
     * @return the Keys Entity
     */
    public KeysEntity keys() {
        return new KeysEntity(client, baseUrl, tokenProvider);
    }

    /**
     * Builder for {@link ManagementAPI} API client instances.
     */
    public static class Builder {
        private final String domain;
        private TokenProvider tokenProvider;
        private Auth0HttpClient httpClient;


        /**
         * Create a new Builder
         * @param domain the domain of the tenant.
         */
        public Builder(String domain) {
            this.domain = domain;
        }

        /**
         * Specify the token to use when making requests. When expired, consumers will need to renew the token and then
         * update the token on the API client instance with {@link ManagementAPI#setApiToken(String)}.
         *
         * @param apiToken the token for use with the Management API.
         * @return this builder instance.
         */
        public Builder withApiToken(String apiToken) {
            this.tokenProvider = SimpleTokenProvider.create(apiToken);
            return this;
        }

        /**
         * Specify a {@link TokenProvider} to use when making requests to the Auth0 Management APIs. This is useful
         * for long-running applications that would prefer the library to manage the token, including renewing it when
         * required.
         * @param tokenProvider
         * @return
         */
        public Builder withTokenProvider(TokenProvider tokenProvider) {
            this.tokenProvider = tokenProvider;
            return this;
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
            return new ManagementAPI(domain, tokenProvider, httpClient == null ?
                DefaultHttpClient.newBuilder().build() : httpClient);
        }
    }
}
