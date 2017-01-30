package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Class that represents an Auth0 Guardian Enrollment object. Related to the {@link com.auth0.client.mgmt.GuardianEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Enrollment {

    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
    @JsonProperty("name")
    private String name;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("auth_method")
    private String authMethod;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("enrolled_at")
    private Date enrolledAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("last_auth")
    private Date lastAuth;

    /**
     * Getter for the enrollment ID
     *
     * @return the id.
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Getter for the enrollment status. Either 'pending' or 'confirmed'.
     *
     * @return the status.
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * Getter for the enrollment type.
     *
     * @return the type.
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Getter for the enrollment name. This is usually the phone number.
     *
     * @return the name.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Getter for the device identifier. This is usually the phone identifier.
     *
     * @return the identifier.
     */
    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Getter for the phone number.
     *
     * @return the phone number.
     */
    @JsonProperty("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter for the authentication method.
     *
     * @return the authentication method.
     */
    @JsonProperty("auth_method")
    public String getAuthMethod() {
        return authMethod;
    }

    /**
     * Getter for the enrolled at.
     *
     * @return the enrolled at.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("enrolled_at")
    public Date getEnrolledAt() {
        return enrolledAt;
    }

    /**
     * Getter for the last authentication.
     *
     * @return the last authentication.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("last_auth")
    public Date getLastAuth() {
        return lastAuth;
    }
}
