package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuardianEnrollment {

    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
    @JsonProperty("name")
    private String name;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("auth_method")
    private String authMethod;
    @JsonProperty("enrolled_at")
    private String enrolledAt;
    @JsonProperty("last_auth")
    private String lastAuth;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public String getEnrolledAt() {
        return enrolledAt;
    }

    public String getLastAuth() {
        return lastAuth;
    }
}
