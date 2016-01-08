package com.auth0.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds the information from a Identity Provider like Facebook or Twitter.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserIdentity implements Parcelable {

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

    protected UserIdentity(Parcel in) {
        id = in.readString();
        connection = in.readString();
        provider = in.readString();
        social = in.readByte() != 0x00;
        accessToken = in.readString();
        accessTokenSecret = in.readString();
        if (in.readByte() == 0x01) {
            profileInfo = (Map<String, Object>) in.readSerializable();
        } else {
            profileInfo = new HashMap<String, Object>();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(connection);
        dest.writeString(provider);
        dest.writeByte((byte) (social ? 0x01 : 0x00));
        dest.writeString(accessToken);
        dest.writeString(accessTokenSecret);
        if (profileInfo == null) {
            dest.writeByte((byte) 0x00);
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeSerializable(new HashMap<String, Object>(profileInfo));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<com.auth0.core.UserIdentity> CREATOR = new Parcelable.Creator<com.auth0.core.UserIdentity>() {
        @Override
        public com.auth0.core.UserIdentity createFromParcel(Parcel in) {
            return new com.auth0.core.UserIdentity(in);
        }

        @Override
        public com.auth0.core.UserIdentity[] newArray(int size) {
            return new com.auth0.core.UserIdentity[size];
        }
    };
}
