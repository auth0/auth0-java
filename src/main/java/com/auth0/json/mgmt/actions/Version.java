package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Represents a version of an action.
 * @see Action
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Version {

    @JsonProperty("id")
    private String id;
    @JsonProperty("code")
    private String code;
    @JsonProperty("dependencies")
    private List<Dependency> dependencies;
    @JsonProperty("deployed")
    private Boolean deployed;
    @JsonProperty("runtime")
    private String runtime;
    @JsonProperty("secrets")
    private List<Secret> secrets;
    @JsonProperty("status")
    private String status;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("errors")
    private List<Error> errors;
    @JsonProperty("action")
    private Action action;
    @JsonProperty("built_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date builtAt;
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date createdAt;
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date updatedAt;

    /**
     * @return the code for this action version.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the dependencies for this action version
     */
    public List<Dependency> getDependencies() {
        return dependencies;
    }

    /**
     * @return whether this action version is deployed or not.
     */
    public Boolean isDeployed() {
        return deployed;
    }

    /**
     * @return the runtime for this action version.
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     * @return the secrets for this action version.
     */
    public List<Secret> getSecrets() {
        return secrets;
    }

    /**
     * @return the status for this action version.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the index of this version in the list of versions for the action.
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @return any errors that occurred while this action version was being built.
     */
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * @return the date when this version was built successfully.
     */
    public Date getBuiltAt() {
        return builtAt;
    }

    /**
     * @return the date when this action version was created.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the date when this action version was updated.
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @return the ID of this action version.
     */
    public String getId() {
        return id;
    }

    /**
     * @return the action to which this version belongs.
     */
    public Action getAction() {
        return action;
    }
}
