package com.auth0.json.mgmt.authenticationmethods;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationMethod implements Serializable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("confirmed")
    private Boolean confirmed;
    @JsonProperty("name")
    private String name;
    @JsonProperty("link_id")
    private String linkId;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("key_id")
    private String keyId;
    @JsonProperty("public_key")
    private String publicKey;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("enrolled_at")
    private Date enrolledAt;
    @JsonProperty("last_auth_at")
    private Date lastAuthedAt;
    @JsonProperty("totp_secret")
    private String totpSecret;
    @JsonProperty("preferred_authentication_method")
    private String preferredAuthenticationMethod;
    @JsonProperty("relying_party_identifier")
    private String relyingPartyIdentifier;
    @JsonProperty("authentication_methods")
    private List<String> authenticationMethods;

    /**
     * Create a new instance.
     */
    @JsonCreator
    public AuthenticationMethod() {
    }

    /**
     * @return the ID of this enrollment.
     */
    public String getId() {
        return id;
    }

    /**
     * @return the type of this enrollment
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of this enrollment
     * Should be ["phone" or "email" or "totp" or "webauthn-roaming"]
     *
     * @param type the type of the enrollment.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the enrollment status
     */
    public Boolean getConfirmed() {
        return confirmed;
    }

    /**
     * @return A human-readable label to identify the authentication method.
     */
    public String getName() {
        return name;
    }

    /**
     * Set a human-readable label to identify the authentication method.
     *
     * @param name A human-readable label to identify the authentication method.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the indication that the authenticator is linked to another authenticator
     */
    public String getLinkId() {
        return linkId;
    }

    /**
     * @return The destination phone number used to send verification codes via text and voice. Applies to phone authentication methods only.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the destination phone number used to send verification codes via text and voice.
     * Applies to phone authentication methods only.
     *
     * @param phoneNumber The destination phone number used to send verification codes via text and voice.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return The email address used to send verification messages. Applies to email authentication methods only.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email address used to send verification messages. Applies to email authentication methods only.
     * @param email The email address used to send verification messages.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The id of the credential. Applies to email webauthn authenticators only.
     */
    public String getKeyId() {
        return keyId;
    }

    /**
     * The id of the credential. Applies to email webauthn authenticators only.
     * @param keyId The id of the credential.
     */
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    /**
     * @return The public key. Applies to email webauthn authenticators only.
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Set the public key. Applies to email webauthn authenticators only.
     * @param publicKey The public key. Applies to email webauthn authenticators only.
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     *
     * @return Authenticator creation date
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return Enrollment date
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("enrolled_at")
    public Date getEnrolledAt() {
        return enrolledAt;
    }

    /**
     * @return Last authentication
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("last_auth_at")
    public Date getLastAuthedAt() {
        return lastAuthedAt;
    }

    /**
     * @return The base32 encoded secret for TOTP generation. Applies to totp authentication methods only.
     */
    public String getTotpSecret() {
        return totpSecret;
    }

    /**
     * Set the base32 encoded secret for TOTP generation. Applies to totp authentication methods only.
     * @param totpSecret The base32 encoded secret for TOTP generation.
     */
    public void setTotpSecret(String totpSecret) {
        this.totpSecret = totpSecret;
    }

    /**
     * @return Preferred phone authentication method.
     */
    public String getPreferredAuthenticationMethod() {
        return preferredAuthenticationMethod;
    }

    /**
     * Set the preferred phone authentication method.
     * @param preferredAuthenticationMethod Preferred phone authentication method.
     */
    public void setPreferredAuthenticationMethod(String preferredAuthenticationMethod) {
        this.preferredAuthenticationMethod = preferredAuthenticationMethod;
    }

    /**
     * @return The relying party identifier. Applies to email webauthn authenticators only.
     */
    public String getRelyingPartyIdentifier() {
        return relyingPartyIdentifier;
    }

    /**
     * Set the relying party identifier. Applies to email webauthn authenticators only.
     * @param relyingPartyIdentifier The relying party identifier. Applies to email webauthn authenticators only.
     */
    public void setRelyingPartyIdentifier(String relyingPartyIdentifier) {
        this.relyingPartyIdentifier = relyingPartyIdentifier;
    }

    /**
     * @return list of authentication methods
     */
    public List<String> getAuthenticationMethods() {
        return authenticationMethods;
    }
}
