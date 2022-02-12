package com.auth0.json.mgmt.branding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandingColors {
    @JsonProperty("primary")
    private String primary;
    @JsonProperty("page_background")
    private String pageBackground;

    /**
     * Getter for the primary color.
     *
     * @return the primary color.
     */
    @JsonProperty("primary")
    public String getPrimary() {
        return primary;
    }

    /**
     * Sets for the primary color.
     */
    @JsonProperty("primary")
    public void setPrimary(String primary) {
        this.primary = primary;
    }

    /**
     * Getter for the color of the page background.
     *
     * @return the page background color.
     */
    @JsonProperty("page_background")
    public String getPageBackground() {
        return pageBackground;
    }

    /**
     * Sets the page background color.
     */
    @JsonProperty("page_background")
    public void setPageBackground(String pageBackground) {
        this.pageBackground = pageBackground;
    }
}
