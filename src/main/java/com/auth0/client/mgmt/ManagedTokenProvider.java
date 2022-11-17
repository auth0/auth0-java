package com.auth0.client.mgmt;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;

import java.time.Instant;
import java.util.Objects;

public class ManagedTokenProvider implements TokenProvider {

    private final AuthAPI authAPI;
    private TokenHolder tokenHolder;
    private final String audience;


    // TODO validate/form domain, accept http client (builder?)
    //  maybe create with an AuthAPI client?? probably too coupled
    public static ManagedTokenProvider create(String domain, String clientId, String clientSecret) {
        return new ManagedTokenProvider(domain, clientId, clientSecret);
    }

    // TODO proper level of synchronization to ensure thread-safety while also not being a deadlock
    // need to synchronize to ensure multiple threads don't race and all fetch tokens when they don't need to
    @Override
    public synchronized String getToken() throws Auth0Exception {
        // get tokens on first request if not set yet
        if (Objects.isNull(tokenHolder)) {
            System.out.println("*********** GETTING TOKEN FOR FIRST TIME ***************");
            tokenHolder = getTokenHolder();
            System.out.println("*********** GOT TOKEN FOR FIRST TIME ***************");
            return tokenHolder.getAccessToken();
        }

        // TODO check logic and think about buffer
        // EXP: 100
        // 99 + 30 = 129
        // 100 < 129? YES, REFRESH
        // EXP: 100
        // 101 + 30 = 131
        // 100 < 131? YES, REFRESH
        // EXP 100
        // 101 - 30 = 71
        // 100 < 71? NO
        if (!tokenHolder.getExpiresAt().toInstant().isBefore(Instant.now().plusSeconds(30))) {
            System.out.println("*********** RETURNING VALID CACHED TOKEN ***************");
            return tokenHolder.getAccessToken();
        }

        System.out.println("*********** REFRESHING TOKEN AND RETURNING NEW API CLIENT ***************");
        tokenHolder = getTokenHolder();
        System.out.println("*********** RETRIEVED NEW TOKEN ***************");
        return tokenHolder.getAccessToken();
    }

    private TokenHolder getTokenHolder() throws Auth0Exception {
        return this.authAPI.requestToken(this.audience).execute().getBody();
    }
    private ManagedTokenProvider(String domain, String clientId, String clientSecret) {
        this.authAPI = new AuthAPI(domain, clientId, clientSecret);
        this.audience = domain + "api/v2/";
    }

    /**
     * Usage:
     * // configured to fetch and manage token
     * ManagementAPI.newBuilder("domain")
     *      .manageToken("clientId", "clientSecret")
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
     */
}
