package com.auth0.json.mgmt.logstreams;

import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Represents an Auth0 Log Stream object. Related to the {@linkplain com.auth0.client.mgmt.LogStreamsEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogStream {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private final String type;
    @JsonProperty("status")
    private String status;
    @JsonProperty("sink")
    private Map<String, Object> sink;

    @JsonProperty("filters")
    private List<LogStreamFilter> filters;

    /**
     * Creates a new LogStream instance and sets the type, which cannot be changed once set.
     *
     * @param type The type of the Log Stream.
     */
    @JsonCreator
    public LogStream(@JsonProperty("type") String type) {
        this.type = type;
    }

    /**
     * Creates a new empty LogStream. Use this when you need to create an instance for use with an update request, as
     * you cannot send the {@code type} field to the update API.
     */
    public LogStream() {
        this(null);
    }

    /**
     * Get the ID of this Log Stream.
     *
     * @return The ID of this Log Stream.
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Get the name of this Log Stream.
     *
     * @return The name of this Log Stream.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this Log Stream.
     *
     * @param name the name to set.
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of this Log Stream.
     *
     * @return The type of this Log Stream.
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Gets the status of this Log Stream.
     *
     * @return The status of this Log Stream.
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this Log Stream.
     *
     * @param status The status to set.
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the sink of this Log Stream.
     *
     * @return the sink of this Log Stream.
     */
    @JsonProperty("sink")
    public Map<String, Object> getSink() {
        return sink;
    }

    /**
     * Sets the sink of this Log Stream.
     *
     * @param sink A key-value map representing the sink to set.
     */
    @JsonProperty("sink")
    public void setSink(Map<String, Object> sink) {
        this.sink = sink;
    }

    /**
     * @return the log stream filters
     */
    @JsonProperty("filters")
    public List<LogStreamFilter> getFilters() {
        return filters;
    }

    /**
     * Set the log stream filters.
     *
     * @param filters the filters to set for the log stream.
     */
    @JsonProperty("filters")
    public void setFilters(List<LogStreamFilter> filters) {
        this.filters = filters;
    }
}
