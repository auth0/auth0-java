package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedOobResponse {

    @JsonProperty("oob_code")
    private String oobCode;

    @JsonProperty("barcode_uri")
    private String barcodeUri;

    @JsonProperty("authenticator_type")
    private String authenticatorType;

    @JsonProperty("oob_channel")
    private String oobChannel;

    @JsonProperty("recovery_codes")
    private List<String> recoveryCodes;

    public String getOobCode() {
        return oobCode;
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

    public String getOobChannel() {
        return oobChannel;
    }
}
