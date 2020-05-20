package com.auth0.json.mgmt.jobs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents a Users Export Job's Field object. Related to the {@link com.auth0.client.mgmt.JobsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersExportField {

    @JsonProperty("name")
    private String name;
    @JsonProperty("export_as")
    private String exportAs;

    public UsersExportField(String name) {
        this.name = name;
    }

    public UsersExportField(@JsonProperty("name") String name, @JsonProperty("export_as") String exportAs) {
        this.name = name;
        this.exportAs = exportAs;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("export_as")
    public String getExportAs() {
        return exportAs;
    }

}
