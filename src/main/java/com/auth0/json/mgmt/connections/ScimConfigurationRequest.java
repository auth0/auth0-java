package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScimConfigurationRequest {
    @JsonProperty("user_id_attribute")
    private String userIdAttribute;
    @JsonProperty("mapping")
    private List<Mapping> mapping;

    /**
     * Creates a new instance.
     */
    public ScimConfigurationRequest() {

    }

    /**
     * Creates a new instance with the given user id attribute and mapping.
     * @param userIdAttribute the user id attribute.
     * @param mapping the mappings.
     */
    public ScimConfigurationRequest(String userIdAttribute, List<Mapping> mapping) {
        this.userIdAttribute = userIdAttribute;
        this.mapping = mapping;
    }

    /**
     * Getter for the user id attribute.
     * @return the user id attribute.
     */
    public String getUserIdAttribute() {
        return userIdAttribute;
    }

    /**
     * Setter for the user id attribute.
     * @param userIdAttribute the user id attribute to set.
     */
    public void setUserIdAttribute(String userIdAttribute) {
        this.userIdAttribute = userIdAttribute;
    }

    /**
     * Getter for the mapping.
     * @return the mapping.
     */
    public List<Mapping> getMapping() {
        return mapping;
    }

    /**
     * Setter for the mapping.
     * @param mapping the mapping to set.
     */
    public void setMapping(List<Mapping> mapping) {
        this.mapping = mapping;
    }
}
