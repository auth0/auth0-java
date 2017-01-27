package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings({"WeakerAccess", "unused"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IOS {

    @JsonProperty("team_id")
    private String teamId;
    @JsonProperty("app_bundle_identifier")
    private String appBundleIdentifier;

    @JsonCreator
    public IOS(@JsonProperty("team_id") String teamId, @JsonProperty("app_bundle_identifier") String appBundleIdentifier) {
        this.teamId = teamId;
        this.appBundleIdentifier = appBundleIdentifier;
    }

    /**
     * Getter for the identifier assigned to the account that signs and upload the app to the store.
     *
     * @return the team identifier.
     */
    @JsonProperty("team_id")
    public String getTeamId() {
        return teamId;
    }

    /**
     * Setter for the identifier assigned to the account that signs and upload the app to the store.
     *
     * @param teamId the team identifier to set.
     */
    @JsonProperty("team_id")
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    /**
     * Getter for the unique identifier assigned by the developer to the app inside the store.
     *
     * @return the bundle identifier.
     */
    @JsonProperty("app_bundle_identifier")
    public String getAppBundleIdentifier() {
        return appBundleIdentifier;
    }

    /**
     * Setter for the unique identifier assigned by the developer to the app inside the store.
     *
     * @param appBundleIdentifier the bundle identifier to set.
     */
    @JsonProperty("app_bundle_identifier")
    public void setAppBundleIdentifier(String appBundleIdentifier) {
        this.appBundleIdentifier = appBundleIdentifier;
    }
}
