package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnabledOrganizations {
    @JsonProperty("organization_id")
    private String organizationId;
    @JsonProperty("assign_membership_on_login")
    private boolean assignMembershipOnLogin;
    @JsonProperty("show_as_button")
    private boolean showAsButton;

    /**
     * Getter for the organization id.
     * @return the organization id.
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Setter for the organization id.
     * @param organizationId the organization id to set.
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * Getter for the assign membership on login.
     * @return the assign membership on login.
     */
    public boolean isAssignMembershipOnLogin() {
        return assignMembershipOnLogin;
    }

    /**
     * Setter for the assign membership on login.
     * @param assignMembershipOnLogin the assign membership on login to set.
     */
    public void setAssignMembershipOnLogin(boolean assignMembershipOnLogin) {
        this.assignMembershipOnLogin = assignMembershipOnLogin;
    }

    /**
     * Getter for the show as button.
     * @return the show as button.
     */
    public boolean isShowAsButton() {
        return showAsButton;
    }

    /**
     * Setter for the show as button.
     * @param showAsButton the show as button to set.
     */
    public void setShowAsButton(boolean showAsButton) {
        this.showAsButton = showAsButton;
    }
}
