package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageCustomization {

    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("html")
    private String html;
    @JsonProperty("show_log_link")
    private Boolean showLogLink;
    @JsonProperty("url")
    private String url;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Boolean getShowLogLink() {
        return showLogLink;
    }

    public void setShowLogLink(Boolean showLogLink) {
        this.showLogLink = showLogLink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
