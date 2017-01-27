package com.auth0.json.mgmt.tenants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorPageCustomization extends PageCustomization {

    @JsonProperty("show_log_link")
    private Boolean showLogLink;
    @JsonProperty("url")
    private String url;

    /**
     * Whether the error page will show a link to the log or not.
     *
     * @return true if the error page will show a link to the log, false otherwise.
     */
    @JsonProperty("show_log_link")
    public Boolean willShowLogLink() {
        return showLogLink;
    }

    /**
     * Sets if the error page will show a link to the log or not.
     *
     * @param showLogLink whether the error page will show a link to the log or not.
     */
    @JsonProperty("show_log_link")
    public void setShowLogLink(Boolean showLogLink) {
        this.showLogLink = showLogLink;
    }

    /**
     * Getter for the url to redirect to instead of showing the default error page.
     *
     * @return the url to redirect to.
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * Setter for the url to redirect to instead of showing the default error page.
     *
     * @param url the url to redirect to.
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }
}
