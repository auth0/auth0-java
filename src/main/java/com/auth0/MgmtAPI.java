package com.auth0;

import com.auth0.json.mgmt.*;
import com.auth0.json.mgmt.client.Client;
import com.auth0.json.mgmt.client.ResourceServer;
import com.auth0.json.mgmt.clientgrant.ClientGrant;
import com.auth0.net.*;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.List;
import java.util.Map;

public class MgmtAPI {

    private final String baseUrl;
    private final String apiToken;
    private final OkHttpClient client;

    public MgmtAPI(String domain, String apiToken) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(apiToken, "api token");

        baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.apiToken = apiToken;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

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


    //ClientGrants

    /**
     * Request all the Client Grants. A token with scope read:client_grants is needed.
     *
     * @return a Request to execute.
     */
    public Request<List<ClientGrant>> listClientGrants() {
        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("client-grants")
                .build()
                .toString();
        CustomRequest<List<ClientGrant>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<ClientGrant>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Client Grant. A token with scope create:client_grants is needed.
     *
     * @param clientId the client id to associate this grant with.
     * @param audience the audience of the grant.
     * @param scope    the scope to grant.
     * @return a Request to execute.
     */
    public Request<ClientGrant> createClientGrant(String clientId, String audience, String[] scope) {
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(audience, "audience");
        Asserts.assertNotNull(scope, "scope");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("client-grants")
                .build()
                .toString();
        CustomRequest<ClientGrant> request = new CustomRequest<>(client, url, "POST", new TypeReference<ClientGrant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.addParameter("client_id", clientId);
        request.addParameter("audience", audience);
        request.addParameter("scope", scope);
        return request;
    }


    /**
     * Delete an existing Client Grant. A token with scope delete:client_grants is needed.
     *
     * @param clientGrantId the client grant id.
     * @return a Request to execute.
     */
    public Request deleteClientGrant(String clientGrantId) {
        Asserts.assertNotNull(clientGrantId, "client grant id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("client-grants")
                .addPathSegment(clientGrantId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Client Grant. A token with scope update:client_grants is needed.
     *
     * @param clientGrantId the client grant id.
     * @param scope         the scope to grant.
     * @return a Request to execute.
     */
    public Request<ClientGrant> updateClientGrant(String clientGrantId, String[] scope) {
        Asserts.assertNotNull(clientGrantId, "client grant id");
        Asserts.assertNotNull(scope, "scope");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("client-grants")
                .addPathSegment(clientGrantId)
                .build()
                .toString();
        CustomRequest<ClientGrant> request = new CustomRequest<>(client, url, "PATCH", new TypeReference<ClientGrant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.addParameter("scope", scope);
        return request;
    }


    //Clients

    /**
     * Request all the Clients. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     *
     * @return a Request to execute.
     */
    public Request<List<Client>> listClients() {
        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
                .build()
                .toString();
        CustomRequest<List<Client>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Client>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request a Client. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     *
     * @param clientId the client id.
     * @return a Request to execute.
     */
    public Request<Client> getClient(String clientId) {
        Asserts.assertNotNull(clientId, "client id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
                .addPathSegment(clientId)
                .build()
                .toString();
        CustomRequest<Client> request = new CustomRequest<>(client, url, "GET", new TypeReference<Client>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Client. A token with scope create:clients is needed.
     *
     * @param client the client data to set.
     * @return a Request to execute.
     */
    public Request<Client> createClient(Client client) {
        Asserts.assertNotNull(client, "client");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
                .build()
                .toString();
        CustomRequest<Client> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<Client>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(client);
        return request;
    }

    /**
     * Delete an existing Client. A token with scope delete:clients is needed.
     *
     * @param clientId the client id.
     * @return a Request to execute.
     */
    public Request deleteClient(String clientId) {
        Asserts.assertNotNull(clientId, "client id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
                .addPathSegment(clientId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Client. A token with scope update:clients is needed. If you also need to update the client_secret and encryption_key attributes the token must have update:client_keys scope.
     *
     * @param clientId the client id.
     * @param client   the client data to set.
     * @return a Request to execute.
     */
    public Request<Client> updateClient(String clientId, Client client) {
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(client, "client");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
                .addPathSegment(clientId)
                .build()
                .toString();
        CustomRequest<Client> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<Client>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(client);
        return request;
    }

    /**
     * Rotates a Client secret. A token with scope update:client_keys is needed. Note that the generated secret is NOT base64 encoded.
     *
     * @param clientId the client id.
     * @return a Request to execute.
     */
    public Request<Client> rotateClientSecret(String clientId) {
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(client, "client");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
                .addPathSegment(clientId)
                .addPathSegment("rotate-secret")
                .build()
                .toString();
        CustomRequest<Client> request = new EmptyBodyRequest<>(this.client, url, "POST", new TypeReference<Client>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }


    //Connections

    /**
     * Request all the Connections. A token with scope read:connections is needed.
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<Connection>> listConnections(ConnectionFilter filter) {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("connections");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<List<Connection>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Connection>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request a Connection. A token with scope read:connections is needed.
     *
     * @param connectionId the id of the connection to retrieve.
     * @param filter       the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<Connection> getConnection(String connectionId, ConnectionFilter filter) {
        Asserts.assertNotNull(connectionId, "connection id");

        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("connections")
                .addPathSegment(connectionId);
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<Connection> request = new CustomRequest<>(client, url, "GET", new TypeReference<Connection>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Connection. A token with scope create:connections is needed.
     *
     * @param connection the connection data to set.
     * @return a Request to execute.
     */
    public Request<Connection> createConnection(Connection connection) {
        Asserts.assertNotNull(connection, "connection");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("connections")
                .build()
                .toString();
        CustomRequest<Connection> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<Connection>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(connection);
        return request;
    }

    /**
     * Delete an existing Connection. A token with scope delete:connections is needed.
     *
     * @param connectionId the connection id.
     * @return a Request to execute.
     */
    public Request deleteConnection(String connectionId) {
        Asserts.assertNotNull(connectionId, "connection id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("connections")
                .addPathSegment(connectionId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Connection. A token with scope update:connections is needed. Note that if the 'options' value is present it will override all the 'options' values that currently exist.
     *
     * @param connectionId the connection id.
     * @param connection   the connection data to set. It can't include name or strategy.
     * @return a Request to execute.
     */
    public Request<Connection> updateConnection(String connectionId, Connection connection) {
        Asserts.assertNotNull(connectionId, "connection id");
        Asserts.assertNotNull(connection, "connection");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("connections")
                .addPathSegment(connectionId)
                .build()
                .toString();
        CustomRequest<Connection> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<Connection>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(connection);
        return request;
    }

    /**
     * Delete an existing User from the given Database Connection. A token with scope delete:users is needed.
     *
     * @param connectionId the connection id where the user is stored.
     * @param email        the email of the user to delete.
     * @return a Request to execute.
     */
    public Request deleteConnectionUser(String connectionId, String email) {
        Asserts.assertNotNull(connectionId, "connection id");
        Asserts.assertNotNull(email, "email");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("connections")
                .addPathSegment(connectionId)
                .addPathSegment("users")
                .addQueryParameter("email", email)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(this.client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }


    //DeviceCredentials

    /**
     * Request all the Device Credentials. A token with scope read:device_credentials is needed.
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<DeviceCredentials>> listDeviceCredentials(DeviceCredentialsFilter filter) {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("device-credentials");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<List<DeviceCredentials>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<DeviceCredentials>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Device Credentials. A token with scope create:current_user_device_credentials is needed.
     *
     * @param deviceCredentials the device credentials data to set.
     * @return a Request to execute.
     */
    public Request<DeviceCredentials> createDeviceCredentials(DeviceCredentials deviceCredentials) {
        Asserts.assertNotNull(deviceCredentials, "device credentials");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("device-credentials")
                .build()
                .toString();
        CustomRequest<DeviceCredentials> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<DeviceCredentials>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(deviceCredentials);
        return request;
    }

    /**
     * Delete an existing Device Credentials. A token with scope delete:device_credentials is needed.
     *
     * @param deviceCredentialsId the device credentials id
     * @return a Request to execute.
     */
    public Request deleteDeviceCredentials(String deviceCredentialsId) {
        Asserts.assertNotNull(deviceCredentialsId, "device credentials id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("device-credentials")
                .addPathSegment(deviceCredentialsId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }


    /**
     * Request all the Log Events. A token with scope read:logs is needed.
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<LogEventsPage> listLogEvents(LogEventFilter filter) {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("logs");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<LogEventsPage> request = new CustomRequest<>(client, url, "GET", new TypeReference<LogEventsPage>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request a Log Event. A token with scope read:logs is needed.
     *
     * @param logEventId the id of the connection to retrieve.
     * @return a Request to execute.
     */
    public Request<LogEvent> getLogEvent(String logEventId) {
        Asserts.assertNotNull(logEventId, "log event id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("logs")
                .addPathSegment(logEventId)
                .build()
                .toString();
        CustomRequest<LogEvent> request = new CustomRequest<>(client, url, "GET", new TypeReference<LogEvent>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }


    //ResourceServers

    /**
     * Request all the Resource Servers. A token with scope read:resource_servers is needed.
     *
     * @return a Request to execute.
     */
    public Request<List<ResourceServer>> listResourceServers() {
        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("resource-servers")
                .build()
                .toString();
        CustomRequest<List<ResourceServer>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<ResourceServer>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request a Resource Server. A token with scope read:resource_servers is needed.
     *
     * @param resourceServerId the id of the resource server to retrieve.
     * @return a Request to execute.
     */
    public Request<ResourceServer> getResourceServer(String resourceServerId) {
        Asserts.assertNotNull(resourceServerId, "resource server id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("resource-servers")
                .addPathSegment(resourceServerId)
                .build()
                .toString();
        CustomRequest<ResourceServer> request = new CustomRequest<>(client, url, "GET", new TypeReference<ResourceServer>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Resource Server. A token with scope create:resource_servers is needed.
     *
     * @param resourceServer the resource server data to set
     * @return a Request to execute.
     */
    public Request<ResourceServer> createResourceServer(ResourceServer resourceServer) {
        Asserts.assertNotNull(resourceServer, "resource server");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("resource-servers")
                .build()
                .toString();
        CustomRequest<ResourceServer> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<ResourceServer>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(resourceServer);
        return request;
    }

    /**
     * Delete an existing Resource Server. A token with scope delete:resource_servers is needed.
     *
     * @param resourceServerId the resource server id
     * @return a Request to execute.
     */
    public Request deleteResourceServer(String resourceServerId) {
        Asserts.assertNotNull(resourceServerId, "resource server id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("resource-servers")
                .addPathSegment(resourceServerId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Resource Server. A token with scope update:resource_servers is needed.
     *
     * @param resourceServerId the resource server id
     * @param resourceServer   the resource server data to set. It can't include identifier.
     * @return a Request to execute.
     */
    public Request<ResourceServer> updateResourceServer(String resourceServerId, ResourceServer resourceServer) {
        Asserts.assertNotNull(resourceServerId, "resource server id");
        Asserts.assertNotNull(resourceServer, "resource server");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("resource-servers")
                .addPathSegment(resourceServerId)
                .build()
                .toString();
        CustomRequest<ResourceServer> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<ResourceServer>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(resourceServer);
        return request;
    }


    //Rules

    /**
     * Request all the Rules. A token with scope read:rules is needed.
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<Rule>> listRules(RulesFilter filter) {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("rules");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<List<Rule>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Rule>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request a Rule. A token with scope read:rules is needed.
     *
     * @param ruleId the id of the rule to retrieve.
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<Rule> getRule(String ruleId, RulesFilter filter) {
        Asserts.assertNotNull(ruleId, "rule id");

        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("rules")
                .addPathSegment(ruleId);
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<Rule> request = new CustomRequest<>(client, url, "GET", new TypeReference<Rule>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Rule. A token with scope create:rules is needed.
     *
     * @param rule the rule data to set
     * @return a Request to execute.
     */
    public Request<Rule> createRule(Rule rule) {
        Asserts.assertNotNull(rule, "rule");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("rules")
                .build()
                .toString();
        CustomRequest<Rule> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<Rule>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(rule);
        return request;
    }

    /**
     * Delete an existing Rule. A token with scope delete:rules is needed.
     *
     * @param ruleId the rule id
     * @return a Request to execute.
     */
    public Request deleteRule(String ruleId) {
        Asserts.assertNotNull(ruleId, "rule id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("rules")
                .addPathSegment(ruleId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Rule. A token with scope update:rules is needed.
     *
     * @param ruleId the rule id
     * @param rule   the rule data to set. It can't include id.
     * @return a Request to execute.
     */
    public Request<Rule> updateRule(String ruleId, Rule rule) {
        Asserts.assertNotNull(ruleId, "rule id");
        Asserts.assertNotNull(rule, "rule");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("rules")
                .addPathSegment(ruleId)
                .build()
                .toString();
        CustomRequest<Rule> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<Rule>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(rule);
        return request;
    }


    //User Blocks

    /**
     * Request all the User Blocks for a given identifier. A token with scope read:users is needed.
     *
     * @param identifier the identifier. Either a username, phone_number, or email.
     * @return a Request to execute.
     */
    public Request<UserBlocks> getUserBlocksByIdentifier(String identifier) {
        Asserts.assertNotNull(identifier, "identifier");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("user-blocks")
                .addQueryParameter("identifier", identifier)
                .build()
                .toString();
        CustomRequest<UserBlocks> request = new CustomRequest<>(client, url, "GET", new TypeReference<UserBlocks>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request all the User Blocks. A token with scope read:users is needed.
     *
     * @param userId the user id.
     * @return a Request to execute.
     */
    public Request<UserBlocks> getUserBlocks(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("user-blocks")
                .addPathSegment(userId)
                .build()
                .toString();
        CustomRequest<UserBlocks> request = new CustomRequest<>(client, url, "GET", new TypeReference<UserBlocks>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Delete any existing User Blocks for a given identifier. A token with scope update:users is needed.
     *
     * @param identifier the identifier. Either a username, phone_number, or email.
     * @return a Request to execute.
     */
    public Request deleteUserBlocksByIdentifier(String identifier) {
        Asserts.assertNotNull(identifier, "identifier");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("user-blocks")
                .addQueryParameter("identifier", identifier)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Delete any existing User Blocks. A token with scope update:users is needed.
     *
     * @param userId the user id.
     * @return a Request to execute.
     */
    public Request deleteUserBlocks(String userId) {
        Asserts.assertNotNull(userId, "user id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("user-blocks")
                .addPathSegment(userId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }


    // Blacklisted Tokens

    /**
     * Request all the Blacklisted Tokens with a given audience. A token with scope blacklist:tokens is needed.
     *
     * @param audience the token audience (aud).
     * @return a Request to execute.
     */
    public Request<List<Token>> getBlacklistedTokens(String audience) {
        Asserts.assertNotNull(audience, "audience");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("blacklists")
                .addPathSegment("tokens")
                .addQueryParameter("aud", audience)
                .build()
                .toString();
        CustomRequest<List<Token>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Token>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Add a Token to the Blacklist. A token with scope blacklist:tokens is needed.
     *
     * @param token the token to blacklist.
     * @return a Request to execute.
     */
    public Request blacklistToken(Token token) {
        Asserts.assertNotNull(token, "token");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("blacklists")
                .addPathSegment("tokens")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "POST");
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(token);
        return request;
    }
}
