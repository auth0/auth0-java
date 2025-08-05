package com.auth0.json.mgmt.networkacls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Action {
    @JsonProperty("block")
    private Boolean block;
    @JsonProperty("allow")
    private Boolean allow;
    @JsonProperty("log")
    private Boolean log;
    @JsonProperty("redirect")
    private Boolean redirect;
    @JsonProperty("redirect_uri")
    private String redirectUri;

    /**
     * Getter for the block action.
     * @return true if the action is to block.
     */
    @JsonProperty("block")
    public Boolean isBlock() {
        return block;
    }

    /**
     * Setter for the block action.
     * @param block true to set the action as block, false otherwise.
     */
    @JsonProperty("block")
    public void setBlock(Boolean block) {
        this.block = block;
    }

    /**
     * Getter for the allow action.
     * @return true if the action is to allow.
     */
    @JsonProperty("allow")
    public Boolean isAllow() {
        return allow;
    }

    /**
     * Setter for the allow action.
     * @param allow true to set the action as allow, false otherwise.
     */
    @JsonProperty("allow")
    public void setAllow(Boolean allow) {
        this.allow = allow;
    }

    /**
     * Getter for the log action.
     * @return true if the action is to log.
     */
    @JsonProperty("log")
    public Boolean isLog() {
        return log;
    }

    /**
     * Setter for the log action.
     * @param log true to set the action as log, false otherwise.
     */
    @JsonProperty("log")
    public void setLog(Boolean log) {
        this.log = log;
    }

    /**
     * Getter for the redirect action.
     * @return true if the action is to redirect.
     */
    @JsonProperty("redirect")
    public Boolean isRedirect() {
        return redirect;
    }

    /**
     * Setter for the redirect action.
     * @param redirect true to set the action as redirect, false otherwise.
     */
    @JsonProperty("redirect")
    public void setRedirect(Boolean redirect) {
        this.redirect = redirect;
    }

    /**
     * Getter for the redirect URI.
     * @return the redirect URI if set, null otherwise.
     */
    public String getRedrectUri() {
        return redirectUri;
    }

    /**
     * Setter for the redirect URI.
     * @param redrectUri the URI to set for redirection.
     */
    public void setRedrectUri(String redrectUri) {
        this.redirectUri = redrectUri;
    }
}
