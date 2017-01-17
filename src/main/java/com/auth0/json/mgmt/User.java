package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("connection")
    private String connection;
    @JsonProperty("password")
    private String password;
    @JsonProperty("verify_password")
    private Boolean verifyPassword;
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("email_verified")
    private Boolean emailVerified;
    @JsonProperty("verify_email")
    private Boolean verifyEmail;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("phone_verified")
    private Boolean phoneVerified;
    @JsonProperty("verify_phone_number")
    private Boolean verifyPhoneNumber;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("picture")
    private String picture;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("identities")
    private List<Identity> identities;
    @JsonProperty("app_metadata")
    private Map<String, Object> appMetadata;
    @JsonProperty("user_metadata")
    private Map<String, Object> userMetadata;
    @JsonProperty("multifactor")
    private List<String> multifactor;
    @JsonProperty("last_ip")
    private String lastIp;
    @JsonProperty("last_login")
    private String lastLogin;
    @JsonProperty("logins_count")
    private Integer loginsCount;
    @JsonProperty("blocked")
    private Boolean blocked;

    @JsonCreator
    public User(@JsonProperty("connection") String connection) {
        this.connection = connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public void setPassword(String password, Boolean needsVerification) {
        this.password = password;
        this.verifyPassword = needsVerification;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email, String clientId, Boolean needsVerification) {
        this.email = email;
        this.clientId = clientId;
        this.verifyEmail = needsVerification;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber, Boolean needsVerification) {
        this.phoneNumber = phoneNumber;
        this.verifyPhoneNumber = needsVerification;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getId() {
        return userId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Identity> getIdentities() {
        return identities;
    }

    public void setIdentities(List<Identity> identities) {
        this.identities = identities;
    }

    public Map<String, Object> getAppMetadata() {
        return appMetadata;
    }

    public void setAppMetadata(Map<String, Object> appMetadata) {
        this.appMetadata = appMetadata;
    }

    public Map<String, Object> getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(Map<String, Object> userMetadata) {
        this.userMetadata = userMetadata;
    }

    public List<String> getMultifactor() {
        return multifactor;
    }

    public void setMultifactor(List<String> multifactor) {
        this.multifactor = multifactor;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getLoginsCount() {
        return loginsCount;
    }

    public void setLoginsCount(Integer loginsCount) {
        this.loginsCount = loginsCount;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    String getPassword() {
        return password;
    }

    Boolean getVerifyPassword() {
        return verifyPassword;
    }

    Boolean getVerifyEmail() {
        return verifyEmail;
    }

    String getClientId() {
        return clientId;
    }

    Boolean getVerifyPhoneNumber() {
        return verifyPhoneNumber;
    }

    String getConnection() {
        return connection;
    }
}