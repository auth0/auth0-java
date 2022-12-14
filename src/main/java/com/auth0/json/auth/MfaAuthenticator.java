package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MfaAuthenticator {

    @JsonProperty("id")
    private String id;

    @JsonProperty("authenticator_type")
    private String authenticatorType;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("name")
    private String name;

    @JsonProperty("oob_channel")
    private String oobChannel;

    public String getId() {
        return id;
    }

    public String getAuthenticatorType() {
        return authenticatorType;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public String getOobChannel() {
        return oobChannel;
    }
}
