package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class that represents an Auth0 Grant object. Related to the {@link com.auth0.client.mgmt.GrantsEntity} entity.
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Grant {

    @JsonProperty("id")
    private String id;
    @JsonProperty("clientID")
    private String clientId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("audience")
    private String audience;
    @JsonProperty("scope")
    private List<String> scope;

    /**
     * Getter for the id of the grant.
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
    @JsonProperty("clientID")
    public String getClientId() {
        return clientId;
    }

    /**
     * Setter for the application's client id.
     *
     * @param clientId the application's client id to set.
     */
    @JsonProperty("clientID")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    /**
     * Getter for the user id.
     *
     * @return the user id.
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * Setter for the user id.
     *
     * @param userId the user id to set.
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
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
