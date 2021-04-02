package com.auth0.json.mgmt.organizations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Represents the organization object.
 * @see com.auth0.client.mgmt.OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("metadata")
    private Map<String, Object> metadata;
    @JsonProperty("branding")
    private Branding branding;

    public Organization() {}

    /**
     * Create a new instance.
     *
     * @param name the name of this organization.
     */
    @JsonCreator
    public Organization(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * @return the ID of this organization.
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name of this Organization.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this Organization.
     *
     * @param name the name of the Organization.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the display name of this Organization.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name of this Organization.
     *
     * @param displayName the display name of this Organization.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the {@linkplain Branding} of this Organization.
     */
    public Branding getBranding() {
        return branding;
    }

    /**
     * Sets the {@linkplain Branding} of this Organization.
     *
     * @param branding the {@linkplain Branding} of this Organization.
     */
    public void setBranding(Branding branding) {
        this.branding = branding;
    }

    /**
     * @return the metadata of this Organization.
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata of this Organization.
     *
     * @param metadata the metadata of this Organization.
     */
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
