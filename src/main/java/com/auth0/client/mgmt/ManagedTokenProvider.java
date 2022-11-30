package com.auth0.client.mgmt;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.client.Auth0HttpClient;

import java.time.Instant;
import java.util.Objects;

public class ManagedTokenProvider implements TokenProvider {

    private final AuthAPI authAPI;
    private TokenHolder tokenHolder;
    private final String audience;


    // TODO validate/form domain, accept http client (builder?)
    public static ManagedTokenProvider create(String domain, String clientId, String clientSecret) {
        return new ManagedTokenProvider(domain, clientId, clientSecret);
    }

    // TODO proper level of synchronization to ensure thread-safety while also not being a deadlock
    //  need to synchronize to ensure multiple threads don't race and all fetch tokens when they don't need to
    @Override
    public synchronized String getToken() throws Auth0Exception {
        // get tokens on first request if not set yet
        if (Objects.isNull(tokenHolder)) {
            System.out.println("*********** GETTING TOKEN FOR FIRST TIME ***************");
            tokenHolder = getTokenHolder();
            System.out.println("*********** GOT TOKEN FOR FIRST TIME ***************");
            return tokenHolder.getAccessToken();
        }

        // token still valid
        // TODO check/test logic
        if (!tokenHolder.getExpiresAt().toInstant().isBefore(Instant.now().plusSeconds(30))) {
            System.out.println("*********** RETURNING VALID CACHED TOKEN ***************");
            return tokenHolder.getAccessToken();
        }

        // new token required, fetch, store, and return
        System.out.println("*********** REFRESHING TOKEN AND RETURNING NEW API CLIENT ***************");
        this.tokenHolder = getTokenHolder();
        System.out.println("*********** RETRIEVED NEW TOKEN ***************");
        return tokenHolder.getAccessToken();
    }

    private TokenHolder getTokenHolder() throws Auth0Exception {
        return this.authAPI.requestToken(this.audience).execute().getBody();
    }
    private ManagedTokenProvider(String domain, String clientId, String clientSecret) {
        this.authAPI = new AuthAPI(domain, clientId, clientSecret);
        this.audience = "https://" + domain + "/api/v2/";
    }

    /**
     * Usage ideas/notes:
     * // configured to fetch and manage token
     * ManagementAPI.newBuilder("domain")
     *      .withManagedToken("clientId", "clientSecret")
     *      .build();
     *
     * // configured just with apiToken, as is today
     * ManagementAPI.newBuilder("domain")
     *     .withAPIToken("apiToken")
     *     .build();
     *
     * // Might be easier to just accept a config object?
     * Configuration config = Configuration.newInstance()
     *      .clientId("clientId")
     *      .clientSecret("clientSecret")
     *      .connectTimeout(5)
     *      // ....
     *      .build();
     *
     * OR, could just accept a TokenProvider:
     * - But this would not also support creation with just plain apiToken or updating the apiToken
     * // simple token provider:
     * TokenProvider simpleTokenProvider = SimpleTokenProvider.create("apiToken");
     * ManagementAPI api = ManagementAPI.newBuilder("domain", simpleTokenProvider);
     *
     * // managed token provider
     * TokenProvider managedTokenProvider = ManagedTokenProvider.create("domain", "clientId", "clientSecret");
     * ManagementAPI api = ManagementAPI.newBuilder("domain", managedTokenProvider);
     */
}
