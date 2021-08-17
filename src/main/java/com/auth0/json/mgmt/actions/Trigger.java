package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents an action's trigger.
 * @see Action
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Trigger {

    @JsonProperty("id")
    private String id;
    @JsonProperty("version")
    private String version;
    @JsonProperty("status")
    private String status;
    @JsonProperty("runtimes")
    private List<String> runtimes;
    @JsonProperty("default_runtime")
    private String defaultRuntime;

    // TODO verify which fields are read-only

    /**
     * @return the ID of this trigger, which represents the type of this trigger.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of this trigger.
     *
     * @param id the ID of this trigger, which represents the type of this trigger.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the version of this trigger.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version of this trigger.
     *
     * @param version the version of this trigger.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the status of this trigger.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this trigger.
     *
     * @param status the status of this trigger.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the runtimes supported by this trigger.
     */
    public List<String> getRuntimes() {
        return runtimes;
    }

    /**
     * Sets the runtimes supported by this trigger.
     * @param runtimes the runtimes supported by this trigger.
     */
    public void setRuntimes(List<String> runtimes) {
        this.runtimes = runtimes;
    }

    /**
     * @return the default runtime of this trigger.
     */
    public String getDefaultRuntime() {
        return defaultRuntime;
    }

    /**
     * Sets the default runtime of this trigger.
     * @param defaultRuntime the default runtime of this trigger.
     */
    public void setDefaultRuntime(String defaultRuntime) {
        this.defaultRuntime = defaultRuntime;
    }
}
