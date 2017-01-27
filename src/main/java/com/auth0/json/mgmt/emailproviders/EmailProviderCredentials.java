package com.auth0.json.mgmt.emailproviders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailProviderCredentials {

    @JsonProperty("api_user")
    private String apiUser;
    @JsonProperty("api_key")
    private String apiKey;
    @JsonProperty("accessKeyId")
    private String accessKeyId;
    @JsonProperty("secretAccessKey")
    private String secretAccessKey;
    @JsonProperty("region")
    private String region;
    @JsonProperty("smtp_host")
    private String smtpHost;
    @JsonProperty("smtp_port")
    private Integer smtpPort;
    @JsonProperty("smtp_user")
    private String smtpUser;
    @JsonProperty("smtp_pass")
    private String smtpPass;

    @JsonCreator
    public EmailProviderCredentials(@JsonProperty("api_key") String apiKey) {
        this.apiKey = apiKey;
    }

    @JsonProperty("api_user")
    public String getApiUser() {
        return apiUser;
    }

    @JsonProperty("api_user")
    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    @JsonProperty("api_key")
    public String getApiKey() {
        return apiKey;
    }

    @JsonProperty("api_key")
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @JsonProperty("accessKeyId")
    public String getAccessKeyId() {
        return accessKeyId;
    }

    @JsonProperty("accessKeyId")
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    @JsonProperty("secretAccessKey")
    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    @JsonProperty("secretAccessKey")
    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("smtp_host")
    public String getSMTPHost() {
        return smtpHost;
    }

    @JsonProperty("smtp_host")
    public void setSMTPHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    @JsonProperty("smtp_port")
    public Integer getSMTPPort() {
        return smtpPort;
    }

    @JsonProperty("smtp_port")
    public void setSMTPPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    @JsonProperty("smtp_user")
    public String getSMTPUser() {
        return smtpUser;
    }

    @JsonProperty("smtp_user")
    public void setSMTPUser(String smtpUser) {
        this.smtpUser = smtpUser;
    }

    @JsonProperty("smtp_pass")
    public String getSMTPPass() {
        return smtpPass;
    }

    @JsonProperty("smtp_pass")
    public void setSMTPPass(String smtpPass) {
        this.smtpPass = smtpPass;
    }
}