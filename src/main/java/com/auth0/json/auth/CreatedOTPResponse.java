package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedOTPResponse {

    @JsonProperty("secret")
    private String secret;

    @JsonProperty("barcode_uri")
    private String barcodeUri;

    @JsonProperty("authenticator_type")
    private String authenticatorType;

    @JsonProperty("recovery_codes")
    private List<String> recoveryCodes;

    public String getSecret() {
        return secret;
    }

    public String getBarcodeUri() {
        return barcodeUri;
    }

    public String getAuthenticatorType() {
        return authenticatorType;
    }

    public List<String> getRecoveryCodes() {
        return recoveryCodes;
    }


    /*
    "secret": "ABCDEFGMK5CE6WTZKRTTQRKUJVFXOVRF",
  "barcode_uri":"otpauth://...",
  "authenticator_type":"otp",
  "recovery_codes":["ABCDEFGDRFK75ABYR7PH8TJA"],
     */
}
