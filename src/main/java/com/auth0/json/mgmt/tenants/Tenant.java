package com.auth0.json.mgmt.tenants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tenant {

    @JsonProperty("change_password")
    private PageCustomization changePassword;
    @JsonProperty("guardian_mfa_page")
    private PageCustomization guardianMfaPage;
    @JsonProperty("default_audience")
    private String defaultAudience;
    @JsonProperty("default_directory")
    private String defaultDirectory;
    @JsonProperty("error_page")
    private PageCustomization errorPage;
    @JsonProperty("flags")
    private Map<String, Boolean> flags;
    @JsonProperty("friendly_name")
    private String friendlyName;
    @JsonProperty("picture_url")
    private String pictureUrl;
    @JsonProperty("support_email")
    private String supportEmail;
    @JsonProperty("support_url")
    private String supportUrl;
    @JsonProperty("allowed_logout_urls")
    private List<String> allowedLogoutUrls;

    @JsonProperty("change_password")
    public PageCustomization getChangePasswordPage() {
        return changePassword;
    }

    @JsonProperty("change_password")
    public void setChangePasswordPage(PageCustomization changePassword) {
        this.changePassword = changePassword;
    }

    public PageCustomization getGuardianMfaPage() {
        return guardianMfaPage;
    }

    public void setGuardianMfaPage(PageCustomization guardianMfaPage) {
        this.guardianMfaPage = guardianMfaPage;
    }

    public String getDefaultAudience() {
        return defaultAudience;
    }

    public void setDefaultAudience(String defaultAudience) {
        this.defaultAudience = defaultAudience;
    }

    public String getDefaultDirectory() {
        return defaultDirectory;
    }

    public void setDefaultDirectory(String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }

    public PageCustomization getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(PageCustomization errorPage) {
        this.errorPage = errorPage;
    }

    public Map<String, Boolean> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, Boolean> flags) {
        this.flags = flags;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getSupportUrl() {
        return supportUrl;
    }

    public void setSupportUrl(String supportUrl) {
        this.supportUrl = supportUrl;
    }

    public List<String> getAllowedLogoutUrls() {
        return allowedLogoutUrls;
    }

    public void setAllowedLogoutUrls(List<String> allowedLogoutUrls) {
        this.allowedLogoutUrls = allowedLogoutUrls;
    }
}