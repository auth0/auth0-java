package com.auth0.json.mgmt.users.sessions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Authentication {
    @JsonProperty("methods")
    private List<AuthenticationMethod> methods;

    /**
     * @return Contains the authentication methods a user has completed during their session
     */
    public List<AuthenticationMethod> getMethods() {
        return methods;
    }
}
