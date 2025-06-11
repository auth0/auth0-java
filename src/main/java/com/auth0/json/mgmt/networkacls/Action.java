package com.auth0.json.mgmt.networkacls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Action {
    @JsonProperty("block")
    private boolean block;
    @JsonProperty("allow")
    private boolean allow;
    @JsonProperty("log")
    private boolean log;
    @JsonProperty("redirect")
    private boolean redirect;
    @JsonProperty("redirect_uri")
    private String redrectUri;

    /**
     * Getter for the block action.
     * @return true if the action is to block.
     */
    @JsonProperty("block")
    public boolean isBlock() {
        return block;
    }

    /**
     * Setter for the block action.
     * @param block true to set the action as block, false otherwise.
     */
    @JsonProperty("block")
    public void setBlock(boolean block) {
        this.block = block;
    }

    /**
     * Getter for the allow action.
     * @return true if the action is to allow.
     */
    @JsonProperty("allow")
    public boolean isAllow() {
        return allow;
    }

    /**
     * Setter for the allow action.
     * @param allow true to set the action as allow, false otherwise.
     */
    @JsonProperty("allow")
    public void setAllow(boolean allow) {
        this.allow = allow;
    }

    /**
     * Getter for the log action.
     * @return true if the action is to log.
     */
    @JsonProperty("log")
    public boolean isLog() {
        return log;
    }

    /**
     * Setter for the log action.
     * @param log true to set the action as log, false otherwise.
     */
    @JsonProperty("log")
    public void setLog(boolean log) {
        this.log = log;
    }

    /**
     * Getter for the redirect action.
     * @return true if the action is to redirect.
     */
    @JsonProperty("redirect")
    public boolean isRedirect() {
        return redirect;
    }

    /**
     * Setter for the redirect action.
     * @param redirect true to set the action as redirect, false otherwise.
     */
    @JsonProperty("redirect")
    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    /**
     * Getter for the redirect URI.
     * @return the redirect URI if set, null otherwise.
     */
    public String getRedrectUri() {
        return redrectUri;
    }

    /**
     * Setter for the redirect URI.
     * @param redrectUri the URI to set for redirection.
     */
    public void setRedrectUri(String redrectUri) {
        this.redrectUri = redrectUri;
    }
}
