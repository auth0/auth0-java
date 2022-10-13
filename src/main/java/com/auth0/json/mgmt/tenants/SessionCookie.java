package com.auth0.json.mgmt.tenants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the value of the {@code session_cookie} field of the {@link Tenant}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionCookie {

    @JsonProperty("mode")
    private String mode;

    @JsonCreator
    public SessionCookie(@JsonProperty("mode") String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
