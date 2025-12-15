package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleWorkspaceProvisioningConfig {
    @JsonProperty("sync_users")
    private boolean syncUsers;

    @JsonProperty("sync_users")
    public boolean isSyncUsers() {
        return syncUsers;
    }

    @JsonProperty("sync_users")
    public void setSyncUsers(boolean syncUsers) {
        this.syncUsers = syncUsers;
    }
}
