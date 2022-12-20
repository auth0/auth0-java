package com.auth0.json.mgmt.rules;

import com.auth0.client.mgmt.RulesConfigsEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Rules Config object. Related to the {@link RulesConfigsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RulesConfig {
    @JsonProperty("key")
    private String key;
    @JsonProperty("value")
    private String value;

    @JsonCreator
    public RulesConfig(@JsonProperty("value") String value) {
        this.value = value;
    }

    /**
     * Getter for the key of the rules config
     *
     * @return the key.
     */
    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    /**
     * Getter for the value of the rules config
     *
     * @return the value.
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * Setter for the value of the rules config.
     *
     * @param value the value to set.
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

}
