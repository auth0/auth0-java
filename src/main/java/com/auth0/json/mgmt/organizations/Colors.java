package com.auth0.json.mgmt.organizations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the colors object of a {@linkplain Branding}.
 * @see com.auth0.client.mgmt.OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Colors {

    @JsonProperty("primary")
    private String primary;
    @JsonProperty("page_background")
    private String pageBackground;

    public Colors() {}

    /**
     * Create a new instance.
     *
     * @param primary the HEX color for primary elements.
     * @param pageBackground the HEX color for background.
     */
    public Colors(@JsonProperty("primary") String primary, @JsonProperty("page_background") String pageBackground) {
        this.primary = primary;
        this.pageBackground = pageBackground;
    }

    /**
     * @return the primary color value.
     */
    public String getPrimary() {
        return primary;
    }

    /**
     * Sets the color for primary elements.
     *
     * @param primary the HEX color for primary elements.
     */
    public void setPrimary(String primary) {
        this.primary = primary;
    }

    /**
     * @return the background color value.
     */
    public String getPageBackground() {
        return pageBackground;
    }

    /**
     * Sets the color for the background.
     *
     * @param pageBackground the HEX color for the background.
     */
    public void setPageBackground(String pageBackground) {
        this.pageBackground = pageBackground;
    }
}
