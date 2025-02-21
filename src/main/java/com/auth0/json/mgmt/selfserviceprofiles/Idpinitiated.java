package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Idpinitiated {
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_protocol")
    private String clientProtocol;
    @JsonProperty("client_authorizequery")
    private String clientAuthorizequery;

    /**
     * Creates a new instance.
     * @return a new instance.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Setter for the enabled.
     * @param enabled the enabled to set.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Getter for the client id.
     * @return the client id.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Setter for the client id.
     * @param clientId the client id to set.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Getter for the client protocol.
     * @return the client protocol.
     */
    public String getClientProtocol() {
        return clientProtocol;
    }

    /**
     * Setter for the client protocol.
     * @param clientProtocol the client protocol to set.
     */
    public void setClientProtocol(String clientProtocol) {
        this.clientProtocol = clientProtocol;
    }

    /**
     * Getter for the client authorizequery.
     * @return the client authorizequery.
     */
    public String getClientAuthorizequery() {
        return clientAuthorizequery;
    }

    /**
     * Setter for the client authorizequery.
     * @param clientAuthorizequery the client authorizequery to set.
     */
    public void setClientAuthorizequery(String clientAuthorizequery) {
        this.clientAuthorizequery = clientAuthorizequery;
    }
}
