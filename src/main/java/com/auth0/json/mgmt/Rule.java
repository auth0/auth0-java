package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Rule object. Related to the {@link com.auth0.client.mgmt.RulesEntity()} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rule {
    @JsonProperty("name")
    private String name;
    @JsonProperty("script")
    private String script;
    @JsonProperty("id")
    private String id;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("order")
    private Integer order;
    @JsonProperty("stage")
    private String stage;

    @JsonCreator
    public Rule(@JsonProperty("name") String name, @JsonProperty("script") String script) {
        this.name = name;
        this.script = script;
    }

    /**
     * Getter for the name of the rule
     *
     * @return the name.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the rule.
     *
     * @param name the name to set.
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the code to be executed when the rule runs.
     *
     * @return the script.
     */
    @JsonProperty("script")
    public String getScript() {
        return script;
    }

    /**
     * Setter for the code to be executed when the rule runs.
     *
     * @param script the script to set.
     */
    @JsonProperty("script")
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * Getter for the rule's identifier.
     *
     * @return the id.
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Whether the rule is enabled or not.
     *
     * @return true if the rule is enabled, false otherwise.
     */
    @JsonProperty("enabled")
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets if the rule is enabled or not.
     *
     * @param enabled whether the rule is enabled or not.
     */
    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Getter for the rule's order in relation to other rules. A rule with a lower order than another rule executes first.
     *
     * @return the order.
     */
    @JsonProperty("order")
    public Integer getOrder() {
        return order;
    }

    /**
     * Setter for the rule's order in relation to other rules. A rule with a lower order than another rule executes first.
     *
     * @param order the order to set.
     */
    @JsonProperty("order")
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * Getter for the rule's execution stage.
     *
     * @return the stage.
     */
    @JsonProperty("stage")
    public String getStage() {
        return stage;
    }
}
