package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IOS {

    private String teamId;
    private String appBundleIdentifier;

    @JsonCreator
    public IOS(@JsonProperty("team_id") String teamId, @JsonProperty("app_bundle_identifier") String appBundleIdentifier) {
        this.teamId = teamId;
        this.appBundleIdentifier = appBundleIdentifier;
    }

    @JsonProperty("team_id")
    public String getTeamId() {
        return teamId;
    }

    @JsonProperty("team_id")
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @JsonProperty("app_bundle_identifier")
    public String getAppBundleIdentifier() {
        return appBundleIdentifier;
    }

    @JsonProperty("app_bundle_identifier")
    public void setAppBundleIdentifier(String appBundleIdentifier) {
        this.appBundleIdentifier = appBundleIdentifier;
    }
}
