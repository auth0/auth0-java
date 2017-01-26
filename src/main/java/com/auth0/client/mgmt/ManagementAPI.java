package com.auth0.client.mgmt;

import com.auth0.utils.Asserts;
import com.auth0.net.TelemetryInterceptor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

public class ManagementAPI {

    private final String baseUrl;
    private final String apiToken;
    private final OkHttpClient client;
    private final TelemetryInterceptor telemetry;
    private final HttpLoggingInterceptor logging;

    public ManagementAPI(String domain, String apiToken) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(apiToken, "api token");

        baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.apiToken = apiToken;

        telemetry = new TelemetryInterceptor();
        logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.NONE);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(telemetry)
                .build();
    }

    /**
     * Avoid sending Telemetry data in every request to the Auth0 servers.
     */
    public void doNotSendTelemetry() {
        telemetry.setEnabled(false);
    }

    /**
     * Whether to enable or not the current Http Logger for every Request, Response and other sensitive information.
     */
    public void setLoggingEnabled(boolean enabled) {
        logging.setLevel(enabled ? Level.BODY : Level.NONE);
    }

    //Visible for testing
    OkHttpClient getClient() {
        return client;
    }

    //Visible for testing
    String getBaseUrl() {
        return baseUrl;
    }

    private String createBaseUrl(String domain) {
        String url = domain;
        if (!domain.startsWith("https://") && !domain.startsWith("http://")) {
            url = "https://" + domain;
        }
        HttpUrl baseUrl = HttpUrl.parse(url);
        return baseUrl == null ? null : baseUrl.newBuilder().build().toString();
    }

    public ClientGrantsEntity clientGrants() {
        return new ClientGrantsEntity(client, baseUrl, apiToken);
    }

    public ClientsEntity clients() {
        return new ClientsEntity(client, baseUrl, apiToken);
    }

    public ConnectionsEntity connections() {
        return new ConnectionsEntity(client, baseUrl, apiToken);
    }

    public DeviceCredentialsEntity deviceCredentials() {
        return new DeviceCredentialsEntity(client, baseUrl, apiToken);
    }

    public LogEventsEntity logEvents() {
        return new LogEventsEntity(client, baseUrl, apiToken);
    }

    public ResourceServersEntity resourceServers() {
        return new ResourceServersEntity(client, baseUrl, apiToken);
    }

    public RulesEntity rules() {
        return new RulesEntity(client, baseUrl, apiToken);
    }

    public UserBlocksEntity userBlocks() {
        return new UserBlocksEntity(client, baseUrl, apiToken);
    }

    public UsersEntity users() {
        return new UsersEntity(client, baseUrl, apiToken);
    }

    public BlacklistsEntity blacklists() {
        return new BlacklistsEntity(client, baseUrl, apiToken);
    }

    public EmailProviderEntity emailProvider() {
        return new EmailProviderEntity(client, baseUrl, apiToken);
    }

    public GuardianEntity guardian() {
        return new GuardianEntity(client, baseUrl, apiToken);
    }

    public StatsEntity stats() {
        return new StatsEntity(client, baseUrl, apiToken);
    }

    public TenantsEntity tenants() {
        return new TenantsEntity(client, baseUrl, apiToken);
    }

    public TicketsEntity tickets() {
        return new TicketsEntity(client, baseUrl, apiToken);
    }
}
