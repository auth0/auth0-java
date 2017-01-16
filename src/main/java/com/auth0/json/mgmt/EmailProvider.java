package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@SuppressWarnings("unused")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDefaultFromAddress() {
        return defaultFromAddress;
    }

    public void setDefaultFromAddress(String defaultFromAddress) {
        this.defaultFromAddress = defaultFromAddress;
    }

    public EmailProviderCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(EmailProviderCredentials credentials) {
        this.credentials = credentials;
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }
}