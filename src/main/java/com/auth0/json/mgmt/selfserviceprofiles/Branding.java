package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Branding {
    @JsonProperty("logo_url")
    private String logoUrl;
    @JsonProperty("colors")
    private Color colors;

    /**
     * Getter for the logo URL.
     * @return the logo URL.
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * Setter for the logo URL.
     * @param logoUrl the logo URL to set.
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * Getter for the colors.
     * @return the colors.
     */
    public Color getColors() {
        return colors;
    }

    /**
     * Setter for the colors.
     * @param colors the colors to set.
     */
    public void setColors(Color colors) {
        this.colors = colors;
    }
}
