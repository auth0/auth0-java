package com.auth0.json.mgmt.users.authenticationmethods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthMethod {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
