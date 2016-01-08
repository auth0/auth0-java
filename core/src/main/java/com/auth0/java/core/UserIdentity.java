package com.auth0.java.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Class that holds the information from a Identity Provider like Facebook or Twitter.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserIdentity {

    private static final String USER_ID_KEY = "user_id";
    private static final String CONNECTION_KEY = "connection";
    private static final String PROVIDER_KEY = "provider";
    private static final String IS_SOCIAL_KEY = "isSocial";
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String ACCESS_TOKEN_SECRET_KEY = "access_token_secret";
    private static final String PROFILE_DATA_KEY = "profileData";
    private String id;
    private String connection;
    private String provider;
    private boolean social;
    private String accessToken;
    private String accessTokenSecret;
    private Map<String, Object> profileInfo;

    @SuppressWarnings("unchecked")
    public UserIdentity(Map<String, Object> values) {
        final Object idValue = values.get(USER_ID_KEY);
        if (idValue != null) {
            this.id = idValue.toString();
        }
        this.connection = (String) values.get(CONNECTION_KEY);
        this.provider = (String) values.get(PROVIDER_KEY);
        this.social = (boolean) values.get(IS_SOCIAL_KEY);
        this.accessToken = (String) values.get(ACCESS_TOKEN_KEY);
        this.accessTokenSecret = (String) values.get(ACCESS_TOKEN_SECRET_KEY);
        this.profileInfo = (Map<String, Object>) values.get(PROFILE_DATA_KEY);
    }

    @JsonCreator
    public UserIdentity(@JsonProperty(value = USER_ID_KEY) String id,
                        @JsonProperty(value = CONNECTION_KEY) String connection,
                        @JsonProperty(value = PROVIDER_KEY) String provider,
                        @JsonProperty(value = IS_SOCIAL_KEY) boolean social,
                        @JsonProperty(value = ACCESS_TOKEN_KEY) String accessToken,
                        @JsonProperty(value = ACCESS_TOKEN_SECRET_KEY) String accessTokenSecret,
                        @JsonProperty(value = PROFILE_DATA_KEY) Map<String, Object> profileInfo) {
        this.id = id;
        this.connection = connection;
        this.provider = provider;
        this.social = social;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
        this.profileInfo = profileInfo;
    }

    public String getId() {
        return id;
    }

    public String getConnection() {
        return connection;
    }

    public String getProvider() {
        return provider;
    }

    public boolean isSocial() {
        return social;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public Map<String, Object> getProfileInfo() {
        return profileInfo;
    }

    public String getUserIdentityId() {
        return String.format("%s|%s", this.provider, this.id);
    }
}
