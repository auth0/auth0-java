package com.auth0.json.mgmt.users;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents an Auth0 User object. Related to the {@link com.auth0.client.mgmt.UsersEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("updated_at")
    private Date updatedAt;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("last_login")
    private Date lastLogin;
    @JsonProperty("logins_count")
    private Integer loginsCount;
    @JsonProperty("blocked")
    private Boolean blocked;
    private Map<String, Object> values;

    public User() {
        this(null);
    }

    @JsonCreator
    public User(@JsonProperty("connection") String connection) {
        this.values = new HashMap<>();
        this.connection = connection;
    }

    /**
     * Setter for the connection this user will be created into.
     *
     * @param connection the connection to set.
     */
    @JsonProperty("connection")
    public void setConnection(String connection) {
        this.connection = connection;
    }

    /**
     * Setter for the password this user will have once created.
     *
     * @param password the password to set.
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the user's username.
     *
     * @return the username.
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the user's username.
     *
     * @param username the username
     */
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the user's email.
     *
     * @return the email.
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the user's email.
     *
     * @param email the email to set.
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Whether the email was verified or not.
     *
     * @return true if the email was verified, false otherwise.
     */
    @JsonProperty("email_verified")
    public Boolean isEmailVerified() {
        return emailVerified;
    }

    /**
     * Sets if the email was verified or not.
     *
     * @param emailVerified whether the email was verified or not.
     */
    @JsonProperty("email_verified")
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     * Getter for the user's phone number (following the E.164 recommendation), only valid for users from SMS connections.
     *
     * @return the user's phone number.
     */
    @JsonProperty("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter for the user's phone number (following the E.164 recommendation), only valid when creating a user on SMS connections.
     *
     * @param phoneNumber the phone number to set.
     */
    @JsonProperty("phone_number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Whether the phone number was verified or not.
     *
     * @return true if the phone number was verified, false otherwise.
     */
    @JsonProperty("phone_verified")
    public Boolean isPhoneVerified() {
        return phoneVerified;
    }

    /**
     * Sets if the phone number was verified or not.
     *
     * @param phoneVerified whether the phone number was verified or not.
     */
    @JsonProperty("phone_verified")
    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    /**
     * Getter for the user's unique identifier.
     *
     * @return the user id.
     */
    @JsonProperty("user_id")
    public String getId() {
        return userId;
    }

    /**
     * Setter for the user's unique identifier. This is only valid when creating a new user, as the property can't change once set.
     * The server will prepend the connection name and a pipe character before the given id value.
     * i.e. For "auth0" connection with user id "123456789" the final user id would be "auth0|123456789".
     */
    @JsonProperty("user_id")
    public void setId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter for the user's picture url.
     *
     * @return the picture url.
     */
    @JsonProperty("picture")
    public String getPicture() {
        return picture;
    }

    /**
     * Setter for the user's picture url.
     *
     * @param picture the picture url to set.
     */
    @JsonProperty("picture")
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * Getter for the user's name.
     *
     * @return the name.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Setter for the user's name.
     *
     * @param name the name to set.
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the user's nickname.
     *
     * @return the nickname.
     */
    @JsonProperty("nickname")
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter for the user's nickname.
     *
     * @param nickname the nickname to set.
     */
    @JsonProperty("nickname")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter for the user's given name.
     *
     * @return the given name.
     */
    @JsonProperty("given_name")
    public String getGivenName() {
        return givenName;
    }

    /**
     * Setter for the user's given name.
     *
     * @param givenName the given name to set.
     */
    @JsonProperty("given_name")
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * Getter for the user's family name.
     *
     * @return the family name.
     */
    @JsonProperty("family_name")
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Setter for the user's family name.
     *
     * @param familyName the family name to set.
     */
    @JsonProperty("family_name")
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * Getter for the date this user was created on.
     *
     * @return the created at.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Getter for the date this user was last updated on.
     *
     * @return the updated at.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Getter for the list of user's identities information. More than one will exists in case accounts are linked.
     *
     * @return the list of user's identities.
     */
    @JsonProperty("identities")
    public List<Identity> getIdentities() {
        return identities;
    }

    /**
     * Getter for the metadata of the application.
     *
     * @return the application metadata.
     */
    @JsonProperty("app_metadata")
    public Map<String, Object> getAppMetadata() {
        return appMetadata;
    }

    /**
     * Setter for the metadata of the application.
     *
     * @param appMetadata the application metadata to set.
     */
    @JsonProperty("app_metadata")
    public void setAppMetadata(Map<String, Object> appMetadata) {
        this.appMetadata = appMetadata;
    }

    /**
     * Getter for the metadata of the user.
     *
     * @return the user metadata.
     */
    @JsonProperty("user_metadata")
    public Map<String, Object> getUserMetadata() {
        return userMetadata;
    }

    /**
     * Setter for the metadata of the user.
     *
     * @param userMetadata the user metadata to set.
     */
    @JsonProperty("user_metadata")
    public void setUserMetadata(Map<String, Object> userMetadata) {
        this.userMetadata = userMetadata;
    }

    /**
     * Getter for the list of multifactor providers that the user has enrolled to.
     *
     * @return the list of enrolled multifactor providers.
     */
    @JsonProperty("multifactor")
    public List<String> getMultifactor() {
        return multifactor;
    }

    /**
     * Getter for the last login IP.
     *
     * @return the last IP.
     */
    @JsonProperty("last_ip")
    public String getLastIP() {
        return lastIp;
    }

    /**
     * Getter for the last login date.
     *
     * @return the last login.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("last_login")
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * Getter for the number of logins for this user.
     *
     * @return the logins count.
     */
    @JsonProperty("logins_count")
    public Integer getLoginsCount() {
        return loginsCount;
    }

    /**
     * Whether the user is blocked or not.
     *
     * @return true if the user is blocked, false otherwise.
     */
    @JsonProperty("blocked")
    public Boolean isBlocked() {
        return blocked;
    }

    /**
     * Sets if the user is blocked or not.
     *
     * @param blocked whether the user is blocked or not.
     */
    @JsonProperty("blocked")
    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * Setter for the Auth0 Application's client ID. Only useful when updating the email.
     *
     * @param clientId the application's client ID to set.
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Sets if a password change should be verified via email.
     *
     * @param verifyPassword true if a password change should be verified via email, false otherwise.
     */
    @JsonProperty("verify_password")
    public void setVerifyPassword(Boolean verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    /**
     * Sets if an email change should be verified.
     *
     * @param verifyEmail true if an email change should be verified, false otherwise.
     */
    @JsonProperty("verify_email")
    public void setVerifyEmail(Boolean verifyEmail) {
        this.verifyEmail = verifyEmail;
    }

    /**
     * Sets if a phone number change should be verified.
     *
     * @param verifyPhoneNumber true if a phone number change should be verified, false otherwise.
     */
    @JsonProperty("verify_phone_number")
    public void setVerifyPhoneNumber(Boolean verifyPhoneNumber) {
        this.verifyPhoneNumber = verifyPhoneNumber;
    }

    @JsonProperty("password")
    String getPassword() {
        return password;
    }

    @JsonProperty("verify_password")
    Boolean willVerifyPassword() {
        return verifyPassword;
    }

    @JsonProperty("verify_email")
    Boolean willVerifyEmail() {
        return verifyEmail;
    }

    @JsonProperty("client_id")
    String getClientId() {
        return clientId;
    }

    @JsonProperty("verify_phone_number")
    Boolean willVerifyPhoneNumber() {
        return verifyPhoneNumber;
    }

    @JsonProperty("connection")
    String getConnection() {
        return connection;
    }

    @JsonAnySetter
    void setValue(String key, Object value) {
        values.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getValues() {
        return values;
    }
}