package com.auth0.json.mgmt.logstreams;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an Auth0 Log Stream Filter object. Related to the {@linkplain com.auth0.client.mgmt.LogStreamsEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogStreamFilter {

    @JsonProperty("type")
    private String type;
    @JsonProperty("name")
    private String name;

    /**
     * Create a new instance.
     *
     * @param type the log stream filter type.
     * @param name the log stream filter name.
     */
    @JsonCreator
    public LogStreamFilter(@JsonProperty("type") String type, @JsonProperty("name") String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * @return the log stream filter type.
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Set the log stream filter type.
     * @param type the log stream filter type.
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the log stream filter name.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
}
