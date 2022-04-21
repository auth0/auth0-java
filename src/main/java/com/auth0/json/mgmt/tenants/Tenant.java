package com.auth0.json.mgmt.tenants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Class that represents an Auth0 Tenant Settings object. Related to the {@link com.auth0.client.mgmt.TenantsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tenant {

    @JsonProperty("change_password")
    private PageCustomization changePassword;
    @JsonProperty("guardian_mfa_page")
    private PageCustomization guardianMFAPage;
    @JsonProperty("default_audience")
    private String defaultAudience;
    @JsonProperty("default_directory")
    private String defaultDirectory;
    @JsonProperty("error_page")
    private ErrorPageCustomization errorPage;
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
    @JsonProperty("session_lifetime")
    private Integer sessionLifetime;

    @JsonProperty("idle_session_lifetime")
    private Integer idleSessionLifetime;

    /**
     * Getter for the change password page customization.
     *
     * @return the page customization.
     */
    @JsonProperty("change_password")
    public PageCustomization getChangePasswordPage() {
        return changePassword;
    }

    /**
     * Setter for the change password customization.
     *
     * @param changePassword the page customization to set.
     */
    @JsonProperty("change_password")
    public void setChangePasswordPage(PageCustomization changePassword) {
        this.changePassword = changePassword;
    }

    /**
     * Getter for the guardian MFA page customization.
     *
     * @return the page customization.
     */
    @JsonProperty("guardian_mfa_page")
    public PageCustomization getGuardianMFAPage() {
        return guardianMFAPage;
    }

    /**
     * Setter for the guardian MFA page customization.
     *
     * @param guardianMFAPage the page customization to set.
     */
    @JsonProperty("guardian_mfa_page")
    public void setGuardianMFAPage(PageCustomization guardianMFAPage) {
        this.guardianMFAPage = guardianMFAPage;
    }

    /**
     * Getter for the default audience used for API Authorization.
     *
     * @return the default audience.
     */
    @JsonProperty("default_audience")
    public String getDefaultAudience() {
        return defaultAudience;
    }

    /**
     * Setter for the default audience used for API Authorization.
     *
     * @param defaultAudience the default audience to set.
     */
    @JsonProperty("default_audience")
    public void setDefaultAudience(String defaultAudience) {
        this.defaultAudience = defaultAudience;
    }

    /**
     * Getter for the name of the connection that will be used for password grants at the token endpoint. Only the following connection types are supported: LDAP, AD, Database Connections, Passwordless, Windows Azure Active Directory, ADFS.
     *
     * @return the default directory.
     */
    @JsonProperty("default_directory")
    public String getDefaultDirectory() {
        return defaultDirectory;
    }

    /**
     * Setter for the name of the connection that will be used for password grants at the token endpoint. Only the following connection types are supported: LDAP, AD, Database Connections, Passwordless, Windows Azure Active Directory, ADFS.
     *
     * @param defaultDirectory the default directory to set.
     */
    @JsonProperty("default_directory")
    public void setDefaultDirectory(String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }

    /**
     * Getter for the error page customization.
     *
     * @return the page customization.
     */
    @JsonProperty("error_page")
    public ErrorPageCustomization getErrorPage() {
        return errorPage;
    }

    /**
     * Setter for the error page customization.
     *
     * @param errorPage the page customization to set.
     */
    @JsonProperty("error_page")
    public void setErrorPage(ErrorPageCustomization errorPage) {
        this.errorPage = errorPage;
    }

    /**
     * Getter for the tenant flags. Some flags are 'change_pwd_flow_v1', 'enable_apis_section',  'disable_impersonation', 'enable_client_connections', 'enable_pipeline2'.
     *
     * @return the tenant flags.
     */
    @JsonProperty("flags")
    public Map<String, Boolean> getFlags() {
        return flags;
    }

    /**
     * Setter for the tenant flags. Some flags are 'change_pwd_flow_v1', 'enable_apis_section',  'disable_impersonation', 'enable_client_connections', 'enable_pipeline2'.
     *
     * @param flags the flags to set.
     */
    @JsonProperty("flags")
    public void setFlags(Map<String, Boolean> flags) {
        this.flags = flags;
    }

    /**
     * Getter for the friendly name of the tenant.
     *
     * @return the friendly name.
     */
    @JsonProperty("friendly_name")
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * Setter for the friendly name of the tenant.
     *
     * @param friendlyName the friendly name to set.
     */
    @JsonProperty("friendly_name")
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /**
     * Getter for the url of the tenant picture.
     *
     * @return the tenant picture url.
     */
    @JsonProperty("picture_url")
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * Setter for the tenant picture url. An image with size 150x150 is recommended.
     *
     * @param pictureUrl the picture url to set.
     */
    @JsonProperty("picture_url")
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    /**
     * Getter for the user support email.
     *
     * @return the support email.
     */
    @JsonProperty("support_email")
    public String getSupportEmail() {
        return supportEmail;
    }

    /**
     * Setter for the user support email.
     *
     * @param supportEmail the support email to set.
     */
    @JsonProperty("support_email")
    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    /**
     * Getter for the user support url.
     *
     * @return the support url.
     */
    @JsonProperty("support_url")
    public String getSupportUrl() {
        return supportUrl;
    }

    /**
     * Setter for the user support url.
     *
     * @param supportUrl the support url to set.
     */
    @JsonProperty("support_url")
    public void setSupportUrl(String supportUrl) {
        this.supportUrl = supportUrl;
    }

    /**
     * Getter for the tenant list of URLs that are valid to redirect to after logout from Auth0.
     *
     * @return the list of logout urls.
     */
    @JsonProperty("allowed_logout_urls")
    public List<String> getAllowedLogoutUrls() {
        return allowedLogoutUrls;
    }

    /**
     * Setter for the tenant list of URLs that are valid to redirect to after logout from Auth0.
     *
     * @param allowedLogoutUrls the allowed logout urls to set.
     */
    @JsonProperty("allowed_logout_urls")
    public void setAllowedLogoutUrls(List<String> allowedLogoutUrls) {
        this.allowedLogoutUrls = allowedLogoutUrls;
    }

    /**
     * Getter for the login session lifetime. This is how long the session will stay valid. Value is in hours.
     *
     * @return the session lifetime in hours.
     */
    @JsonProperty("session_lifetime")
    public Integer getSessionLifetime() {
        return sessionLifetime;
    }

    /**
     * Setter for the login session lifetime. This is how long the session will stay valid. Value is in hours.
     *
     * @param sessionLifetime the session lifetime in hours to set.
     */
    @JsonProperty("session_lifetime")
    public void setSessionLifetime(Integer sessionLifetime) {
        this.sessionLifetime = sessionLifetime;
    }

    /**
     * Getter for the login session idle lifetime. This is how long the session will stay valid without user activity. Value is in hours.
     *
     * @return the session idle lifetime in hours.
     */
    @JsonProperty("idle_session_lifetime")
    public Integer getIdleSessionLifetime() {
        return idleSessionLifetime;
    }

    /**
     * Setter for the login session idle lifetime. This is how long the session will stay valid without user activity. Value is in hours, and decimals are allowed (for 30 minutes, use 0.5).
     *
     * @param idleSessionLifetime the session lifetime in hours to set.
     */
    @JsonProperty("idle_session_lifetime")
    public void setIdleSessionLifetime(Integer idleSessionLifetime) {
        this.idleSessionLifetime = idleSessionLifetime;
    }
}
