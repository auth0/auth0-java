package com.auth0.json.mgmt.jobs;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;

/**
 * Class that represents an Auth0 Job object. Related to the {@link com.auth0.client.mgmt.JobsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Job {

    @JsonProperty("status")
    private final String status;
    @JsonProperty("type")
    private final String type;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("id")
    private final String id;
    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("connection")
    private String connection;
    @JsonProperty("location")
    private String location;
    @JsonProperty("percentage_done")
    private Integer percentageDone;
    @JsonProperty("external_id")
    private String externalId;
    @JsonProperty("time_left_seconds")
    private Integer timeLeftSeconds;
    @JsonProperty("format")
    private String format;
    @JsonProperty("summary")
    private JobSummary summary;

    @JsonCreator
    private Job(@JsonProperty("status") String status, @JsonProperty("type") String type, @JsonProperty("id") String id) {
        this.status = status;
        this.type = type;
        this.id = id;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Getter for the connection id this job uses.
     *
     * @return the connection id.
     */
    @JsonProperty("connection_id")
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Getter for the name of the connection this job uses.
     *
     * @return the connection name.
     */
    @JsonProperty("connection")
    public String getConnection() {
        return connection;
    }

    /**
     * Getter for the format this job will output.
     *
     * @return the format.
     */
    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    /**
     * Getter for the URL to download the result of the job.
     *
     * @return the location.
     */
    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    /**
     * Getter for the completion percentage of this job.
     *
     * @return the percentage done.
     */
    @JsonProperty("percentage_done")
    public Integer getPercentageDone() {
        return percentageDone;
    }

    /**
     * Getter for the external ID used to correlate multiple jobs.
     *
     * @return the external id.
     */
    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    /**
     * Getter for the estimated time remaining before job completes.
     *
     * @return the time left in seconds.
     */
    @JsonProperty("time_left_seconds")
    public Integer getTimeLeftSeconds() {
        return timeLeftSeconds;
    }

    /**
     * Getter for the summary of a completed Auth0 Job.
     *
     * @return the summary of the Job.
     */
    @JsonProperty("summary")
    public JobSummary getSummary() {
        return summary;
    }
}
