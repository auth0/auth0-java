package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Represents the Action entity.
 *
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Action {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("supported_triggers")
    private List<Trigger> supportedTriggers;
    @JsonProperty("code")
    private String code;
    @JsonProperty("dependencies")
    private List<Dependency> dependencies;
    @JsonProperty("runtime")
    private String runtime;
    @JsonProperty("secrets")
    private List<Secret> secrets;
    @JsonProperty("deployed_version")
    private Version deployedVersion;
    @JsonProperty("status")
    private String status;
    @JsonProperty("all_changes_deployed")
    private Boolean allChangesDeployed;
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date createdAt;
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date updatedAt;

    /**
     * Create a new instance.
     * @param name the name of the action
     * @param supportedTriggers the action's supported triggers
     */
    // TODO should we require code, dependencies, and runtime as the TODO in schema suggests?
    @JsonCreator
    public Action(@JsonProperty("name") String name, @JsonProperty("supported_triggers") List<Trigger> supportedTriggers) {
        this.name = name;
        this.supportedTriggers = supportedTriggers;
    }

    /**
     * Create a new instance.
     */
    public Action() {}

    /**
     * @return the supported triggers for this action.
     */
    public List<Trigger> getSupportedTriggers() {
        return this.supportedTriggers;
    }

    /**
     * Set the supported triggers for this action.
     *
     * @param supportedTriggers the supported triggers.
     */
    public void setSupportedTriggers(List<Trigger> supportedTriggers) {
        this.supportedTriggers = supportedTriggers;
    }

    /**
     * @return the ID of this action.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the name of this action.
     * @param name the name of the action.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name of this action.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the created at date of this action.
     */
    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * @return the updated at date of this action.
     */
    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * @return the code of this action.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Sets the code of this action.
     * @param code the code of this action.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the dependencies of this action.
     */
    public List<Dependency> getDependencies() {
        return this.dependencies;
    }

    /**
     * Sets the dependencies of this action.
     * @param dependencies the dependencies of this action.
     */
    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * @return the runtime of this action.
     */
    public String getRuntime() {
        return this.runtime;
    }

    /**
     * Sets the runtime of this action.
     * @param runtime the runtime of this action.
     */
    public void  setRuntime(String runtime) {
        this.runtime = runtime;
    }

    /**
     * @return the status of this action.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * @return the secrets of this action.
     */
    public List<Secret> getSecrets() {
        return this.secrets;
    }

    /**
     * Sets the secrets of this action.
     * @param secrets the secrets of this action.
     */
    public void setSecrets(List<Secret> secrets) {
        this.secrets = secrets;
    }

    /**
     * @return whether all changes have been deployed for this action.
     */
    public Boolean getAllChangesDeployed() {
        return this.allChangesDeployed;
    }

    /**
     * @return the deployed version of this action.
     */
    public Version getDeployedVersion() {
        return this.deployedVersion;
    }
}
