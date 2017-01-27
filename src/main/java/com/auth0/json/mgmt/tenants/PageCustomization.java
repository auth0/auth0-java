package com.auth0.json.mgmt.tenants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageCustomization {

    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("html")
    private String html;

    /**
     * Whether the custom page will be used instead of the default one or not.
     *
     * @return true if the custom page will be used instead of the default one, false otherwise.
     */
    @JsonProperty("enabled")
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets if the custom page will be used instead of the default one or not.
     *
     * @param enabled whether the custom page will be used instead of the default one, false otherwise.
     */
    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Getter for the page custom HTML code.
     *
     * @return the HTML code.
     */
    @JsonProperty("html")
    public String getHTML() {
        return html;
    }

    /**
     * Setter for the page custom HTML code.
     *
     * @param html the HTML code to set.
     */
    @JsonProperty("html")
    public void setHTML(String html) {
        this.html = html;
    }

}
