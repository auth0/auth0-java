package com.auth0.json.mgmt.permissions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Permission's Source object. Related to the {@link com.auth0.client.mgmt.UsersEntity} entity.
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionSource {

    @JsonProperty("source_type")
    private String type;

    @JsonProperty("source_name")
    private String name;

    @JsonProperty("source_id")
    private String id;

    /**
     * Getter for the source's name.
     *
     * @return the name of the source.
     */
    @JsonProperty("source_name")
    public String getName() {
        return name;
    }

    /**
     * Getter for the source's type.
     *
     * @return the type of the source.
     */
    @JsonProperty("source_type")
    public String getType() {
        return type;
    }

    /**
     * Getter for the source's id.
     *
     * @return the id of the source.
     */
    @JsonProperty("source_id")
    public String getId() {
        return id;
    }

}
