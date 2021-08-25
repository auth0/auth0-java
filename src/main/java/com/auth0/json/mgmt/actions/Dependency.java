package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an Action's dependencies.
 * @see Action
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dependency {

    @JsonProperty("name")
    private String name;
    @JsonProperty("version")
    private String version;
    @JsonProperty("registry_url")
    private String registryUrl;

    /**
     * Creates a new instance.
     * @param name the name of this dependency, e.g., "lodash"
     * @param version the version of this dependency, e.g., 4.17.1
     */
    @JsonCreator
    public Dependency(@JsonProperty("name") String name, @JsonProperty("version") String version) {
        this.name = name;
        this.version = version;
    }

    /**
     * Creates a new dependency
     */
    public Dependency() {}

    /**
     * @return the name of this dependency.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this dependency.
     * @param name the name of this dependency, e.g., "lodash"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the version of this dependency.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version of this dependency.
     * @param version the version of this dependency, e.g., 4.17.1
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the registry URL of this dependency.
     */
    public String getRegistryUrl() {
        return registryUrl;
    }

    /**
     * Sets the registry URL of this dependency. This is optional, and primarily used for private npm modules.
     * @param registryUrl the registry URL of this dependency.
     */
    public void setRegistryUrl(String registryUrl) {
        this.registryUrl = registryUrl;
    }
}
