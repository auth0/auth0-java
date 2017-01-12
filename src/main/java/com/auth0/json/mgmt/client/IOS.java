package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getAppBundleIdentifier() {
        return appBundleIdentifier;
    }

    public void setAppBundleIdentifier(String appBundleIdentifier) {
        this.appBundleIdentifier = appBundleIdentifier;
    }
}
