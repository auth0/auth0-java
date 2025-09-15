package com.auth0.json.mgmt.networkacls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rule {
    @JsonProperty("action")
    private Action action;
    @JsonProperty("match")
    private Match match;
    @JsonProperty("not_match")
    private Match notMatch;
    @JsonProperty("scope")
    private String scope;

    /**
     * Getter for the action associated with the rule.
     * @return the action.
     */
    public Action getAction() {
        return action;
    }

    /**
     * Setter for the action associated with the rule.
     * @param action the action to set.
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * Getter for the match criteria of the rule.
     * @return the match criteria.
     */
    public Match getMatch() {
        return match;
    }

    /**
     * Setter for the match criteria of the rule.
     * @param match the match criteria to set.
     */
    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * Getter for the not match criteria of the rule.
     * @return the not match criteria.
     */
    public Match getNotMatch() {
        return notMatch;
    }

    /**
     * Setter for the not match criteria of the rule.
     * @param notMatch the not match criteria to set.
     */
    public void setNotMatch(Match notMatch) {
        this.notMatch = notMatch;
    }

    /**
     * Getter for the scope of the rule.
     * @return the scope of the rule.
     */
    public String getScope() {
        return scope;
    }

    /**
     * Setter for the scope of the rule.
     * @param scope the scope to set for the rule.
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
}
