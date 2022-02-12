package com.auth0.json.mgmt.branding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandingSettings {
    @JsonProperty("colors")
    private BrandingColors colors;
    @JsonProperty("favicon_url")
    private String faviconUrl;
    @JsonProperty("logo_url")
    private String logoUrl;
    @JsonProperty("font")
    private BrandingFont font;

    /**
     * Getter for the colors of the branding.
     *
     * @return the colors of the branding.
     */
    @JsonProperty("colors")
    public BrandingColors getColors() {
        return colors;
    }

    /**
     * Sets the colors of the branding.
     */
    @JsonProperty("colors")
    public void setColors(BrandingColors colors) {
        this.colors = colors;
    }

    /**
     * Getter for the favicon URL.
     *
     * @return the favicon URL.
     */
    @JsonProperty("favicon_url")
    public String getFaviconUrl() {
        return faviconUrl;
    }

    /**
     * Sets the favicon URL.
     */
    @JsonProperty("favicon_url")
    public void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    /**
     * Getter for the logo URL.
     *
     * @return the logo URL.
     */
    @JsonProperty("logo_url")
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * Sets the logo URL.
     */
    @JsonProperty("logo_url")
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * Getter for the font.
     *
     * @return the font.
     */
    @JsonProperty("font")
    public BrandingFont getFont() {
        return font;
    }

    /**
     * Sets the font.
     */
    @JsonProperty("font")
    public void setFont(BrandingFont font) {
        this.font = font;
    }
}
