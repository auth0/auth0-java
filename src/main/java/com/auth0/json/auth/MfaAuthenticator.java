package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MfaAuthenticator {

    //  id: String
    @JsonProperty("id")
    private String id;

    //  authenticator_tyupe: String
    @JsonProperty("authenticator_type")
    private String authenticatorType;

    //  active: boolean
    @JsonProperty("active")
    private boolean active;

    //  name: String (may be null)
    @JsonProperty("name")
    private String name;

    //  oob_channel: String (may be null)
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
