package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MfaChallengeResponse {

    @JsonProperty("challenge_type")
    private String challengeType;

    @JsonProperty("binding_method")
    private String bindingMethod;

    @JsonProperty("oob_code")
    private String oobCode;

    public String getChallengeType() {
        return challengeType;
    }

    public String getBindingMethod() {
        return bindingMethod;
    }

    public String getOobCode() {
        return oobCode;
    }
}
