package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordlessSmsResponse {
    @JsonProperty("_id")
    private String id;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("phone_verified")
    private Boolean phoneVerified;
    @JsonProperty("request_language")
    private String requestLanguage;

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phone_verified")
    public Boolean isPhoneVerified() {
        return phoneVerified;
    }

    @JsonProperty("request_language")
    public String getRequestLanguage() {
        return requestLanguage;
    }
}
