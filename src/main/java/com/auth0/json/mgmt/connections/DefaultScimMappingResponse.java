package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultScimMappingResponse {
    @JsonProperty("mapping")
    private List<Mapping> mapping;

    /**
     * Creates a new instance.
     */
    @JsonCreator
    public DefaultScimMappingResponse() {
    }

    /**
     * Creates a new instance with the given mapping.
     * @param mapping the mapping attribute.
     */
    @JsonCreator
    public DefaultScimMappingResponse(@JsonProperty("mapping") List<Mapping> mapping) {
        this.mapping = mapping;
    }

    /**
     * Getter for the mapping attribute.
     * @return the mapping attribute.
     */
    public List<Mapping> getMapping() {
        return mapping;
    }

    /**
     * Setter for the mapping attribute.
     * @param mapping the mapping attribute to set.
     */
    public void setMapping(List<Mapping> mapping) {
        this.mapping = mapping;
    }
}
