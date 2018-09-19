package com.auth0.json.mgmt.jobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Class that represents an Auth0 Users Export object. Related to the {@link com.auth0.client.mgmt.JobsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersExport {

    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("format")
    private String format;
    @JsonProperty("limit")
    private int limit;
    @JsonProperty("fields")
    private List<List<Map<String, String>>> fields = null;

    @JsonCreator
    public UsersExport() {}

    @JsonCreator
    public UsersExport(
            @JsonProperty("connection_id") String connectionId,
            @JsonProperty("format") String format,
            @JsonProperty("limit") int limit,
            @JsonProperty("fields") List<List<Map<String, String>>> fields) {
        this.connectionId = connectionId;
        this.format = format;
        this.limit = limit;
        this.fields = fields;
    }

    @JsonProperty("connection_id")
    public String getConnectionId() { return connectionId; }

    @JsonProperty("connection_id")
    public void setConnectionId(final String connectionId) {
        this.connectionId = connectionId;
    }

    @JsonProperty("format")
    public String getFormat() { return format; }

    @JsonProperty("format")
    public void setFormat(final String format) {
        this.format = format;
    }

    @JsonProperty("limit")
    public int getLimit() { return limit; }

    @JsonProperty("limit")
    public void setFields(final List<List<Map<String, String>>> fields) {
        this.fields = fields;
    }

    @JsonProperty("fields")
    public List<List<Map<String, String>>> getFields() { return fields; }

    @JsonProperty("fields")
    public void setLimit(final int limit) {
        this.limit = limit;
    }
}
