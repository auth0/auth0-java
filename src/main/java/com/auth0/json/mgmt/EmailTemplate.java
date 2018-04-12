package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Guardian Factor object. Related to the {@link com.auth0.client.mgmt.GuardianEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailTemplate {

    @JsonProperty("template")
    private String name;
    @JsonProperty("body")
    private String body;
    @JsonProperty("from")
    private String from;
    @JsonProperty("resultUrl")
    private String resultUrl;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("syntax")
    private String syntax;
    @JsonProperty("urlLifetimeInSeconds")
    private Integer urlLifetimeInSeconds;
    @JsonProperty("enabled")
    private Boolean enabled;

    /**
     * Getter for the name of the template.
     *
     * @return the name.
     */
    @JsonProperty("template")
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the template.
     */
    @JsonProperty("template")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the template code.
     *
     * @return the template code.
     */
    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    /**
     * Sets the template code
     *
     * @param body the code this template will have
     */
    @JsonProperty("body")
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Getter for the sender of the email
     *
     * @return the sender of the email
     */
    @JsonProperty("from")
    public String getFrom() {
        return from;
    }

    /**
     * Sets the sender of the email
     *
     * @param from the sender of the email
     */
    @JsonProperty("from")
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Getter the URL to redirect the user to after a successful action.
     *
     * @return the URL to redirect the user to after a successful action.
     */
    @JsonProperty("resultUrl")
    public String getResultUrl() {
        return resultUrl;
    }

    /**
     * Sets the URL to redirect the user to after a successful action.
     *
     * @param resultUrl the URL to redirect the user to after a successful action.
     */
    @JsonProperty("resultUrl")
    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    /**
     * Getter for the subject of the email.
     *
     * @return the subject of the email.
     */
    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of the email.
     *
     * @param subject the subject of the email.
     */
    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Getter for the syntax used in the template's code.
     *
     * @return the syntax used in the template's code.
     */
    @JsonProperty("syntax")
    public String getSyntax() {
        return syntax;
    }

    /**
     * Sets for the syntax to be used in the template's code.
     *
     * @param syntax the syntax to be used in the template's code.
     */
    @JsonProperty("syntax")
    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    /**
     * Getter for the lifetime in seconds that the link within the email will be valid for.
     *
     * @return the lifetime in seconds that the link within the email will be valid for.
     */
    @JsonProperty("urlLifetimeInSeconds")
    public Integer getUrlLifetimeInSeconds() {
        return urlLifetimeInSeconds;
    }

    /**
     * Sets the lifetime in seconds that the link within the email will be valid for.
     *
     * @param urlLifetimeInSeconds the lifetime in seconds that the link within the email will be valid for.
     */
    @JsonProperty("urlLifetimeInSeconds")
    public void setUrlLifetimeInSeconds(Integer urlLifetimeInSeconds) {
        this.urlLifetimeInSeconds = urlLifetimeInSeconds;
    }

    /**
     * Whether or not this template is enabled.
     *
     * @return true if this template is enabled, false otherwise.
     */
    @JsonProperty("enabled")
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Enables or disables this template.
     *
     * @param enabled whether this template is enabled or not.
     */
    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
