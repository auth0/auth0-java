package com.auth0.json.mgmt.organizations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the branding object of an {@linkplain Organization}.
 * @see com.auth0.client.mgmt.OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Branding {

    @JsonProperty("logo_url")
    private String logoUrl;
    @JsonProperty("colors")
    private Colors colors;

    /**
     * @return the value of the logo URL
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * Sets the value of the logo URL to display on the login page
     *
     * @param logoUrl the logo URL to display on the login page. Must be an HTTPS URL or null.
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * @return the {@linkplain Colors} for this Branding instance.
     */
    public Colors getColors() {
        return colors;
    }

    /**
     * Sets the value of the {@linkplain Colors} for this Branding instance.
     *
     * @param colors the colors associated with this Branding instance.
     */
    public void setColors(Colors colors) {
        this.colors = colors;
    }
}
