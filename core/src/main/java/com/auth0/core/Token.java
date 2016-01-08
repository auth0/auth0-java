package com.auth0.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that holds a user's token information.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token implements Parcelable {

    private String idToken;
    private String accessToken;
    private String type;
    private String refreshToken;

    public Token(@JsonProperty(value = "id_token", required = true) String idToken,
                 @JsonProperty(value = "access_token") String accessToken,
                 @JsonProperty(value = "token_type") String type,
                 @JsonProperty(value = "refresh_token") String refreshToken) {
        this.idToken = idToken;
        this.accessToken = accessToken;
        this.type = type;
        this.refreshToken = refreshToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getType() {
        return type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    protected Token(Parcel in) {
        idToken = in.readString();
        accessToken = in.readString();
        type = in.readString();
        refreshToken = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idToken);
        dest.writeString(accessToken);
        dest.writeString(type);
        dest.writeString(refreshToken);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<com.auth0.core.Token> CREATOR = new Parcelable.Creator<com.auth0.core.Token>() {
        @Override
        public com.auth0.core.Token createFromParcel(Parcel in) {
            return new com.auth0.core.Token(in);
        }

        @Override
        public com.auth0.core.Token[] newArray(int size) {
            return new com.auth0.core.Token[size];
        }
    };
}
