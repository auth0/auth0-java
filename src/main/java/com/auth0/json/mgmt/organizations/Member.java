package com.auth0.json.mgmt.organizations;

import com.auth0.json.mgmt.roles.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the member of an organization.
 * @see com.auth0.client.mgmt.OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("picture")
    private String picture;
    @JsonProperty("name")
    private String name;
    @JsonProperty("roles")
    private List<Role> roles;

    /**
     * @return the user ID of this Member.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return the user ID of this Member.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of this Member.
     *
     * @param email the email of this Member.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the URL for this Member's picture.
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Sets the URL for this Member's picture.
     *
     * @param picture the URL of this Member's picture.
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * @return the name of this Member.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this Member.
     *
     * @param name the name of this Member.
     */
    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
