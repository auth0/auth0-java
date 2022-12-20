package com.auth0.json.mgmt.clientgrants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class that represents an Auth0 Client Grant object. Related to the {@link com.auth0.client.mgmt.ClientGrantsEntity} entity.
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientGrant {

    @JsonProperty("id")
    private String id;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("audience")
    private String audience;
    @JsonProperty("scope")
    private List<String> scope;

    /**
     * Getter for the id of the client grant.
     *
     * @return the id.
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Getter for the client id of the application.
     *
     * @return the application's client id.
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * Setter for the application's client id.
     *
     * @param clientId the application's client id to set.
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Getter for the audience.
     *
     * @return the audience.
     */
    @JsonProperty("audience")
    public String getAudience() {
        return audience;
    }

    /**
     * Setter for the audience.
     *
     * @param audience the audience to set.
     */
    @JsonProperty("audience")
    public void setAudience(String audience) {
        this.audience = audience;
    }

    /**
     * Getter for the scope.
     *
     * @return the scope.
     */
    @JsonProperty("scope")
    public List<String> getScope() {
        return scope;
    }

    /**
     * Setter for the scope.
     *
     * @param scope the scope to set.
     */
    @JsonProperty("scope")
    public void setScope(List<String> scope) {
        this.scope = scope;
    }
}
