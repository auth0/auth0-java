package com.auth0.json.mgmt.emailproviders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Class that represents an Auth0 Email Provider object. Related to the {@link com.auth0.client.mgmt.EmailProviderEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailProvider {

    @JsonProperty("name")
    private String name;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("default_from_address")
    private String defaultFromAddress;
    @JsonProperty("credentials")
    private EmailProviderCredentials credentials;
    @JsonProperty("settings")
    private Map<String, Object> settings;

    @JsonCreator
    public EmailProvider(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Getter for the provider name.
     *
     * @return the provider name.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Setter for the provider name.
     *
     * @param name the name to set.
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Whether this provider is enabled or not.
     *
     * @return true if this provider is enabled, false otherwise.
     */
    @JsonProperty("enabled")
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets if this provider is enabled or not.
     *
     * @param enabled whether this provider is enabled or not.
     */
    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Getter for the default from address.
     *
     * @return the default from address.
     */
    @JsonProperty("default_from_address")
    public String getDefaultFromAddress() {
        return defaultFromAddress;
    }

    /**
     * Setter for the default from address.
     *
     * @param defaultFromAddress the default from address.
     */
    @JsonProperty("default_from_address")
    public void setDefaultFromAddress(String defaultFromAddress) {
        this.defaultFromAddress = defaultFromAddress;
    }

    /**
     * Getter for the provider credentials.
     *
     * @return the provider credentials.
     */
    @JsonProperty("credentials")
    public EmailProviderCredentials getCredentials() {
        return credentials;
    }

    /**
     * Setter for the provider credentials.
     *
     * @param credentials the provider credentials to set.
     */
    @JsonProperty("credentials")
    public void setCredentials(EmailProviderCredentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Getter for the specific provider settings.
     *
     * @return the provider settings.
     */
    @JsonProperty("settings")
    public Map<String, Object> getSettings() {
        return settings;
    }

    /**
     * Setter for the specific provider settings.
     *
     * @param settings the provider settings to set.
     */
    @JsonProperty("settings")
    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }
}