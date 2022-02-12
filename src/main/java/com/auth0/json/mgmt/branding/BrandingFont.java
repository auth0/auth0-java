package com.auth0.json.mgmt.branding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandingFont {
    @JsonProperty("url")
    private String url;

    /**
     * Getter for the url of the font.
     *
     * @return the url.
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url of the font.
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }
}
