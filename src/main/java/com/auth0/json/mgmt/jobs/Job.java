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
    private String status;
    @JsonProperty("type")
    private String type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("id")
    private String id;
    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("location")
    private String location;
    @JsonProperty("percentage_done")
    private Integer percentageDone;
    @JsonProperty("time_left_seconds")
    private Integer timeLeftSeconds;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
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
     * @return the connection id
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Getter for the URL to download the result of the job.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for the completion percentage of this job.
     *
     * @return the percentage done
     */
    public Integer getPercentageDone() {
        return percentageDone;
    }

    /**
     * Getter for the estimated time remaining before job completes.
     *
     * @return the time left in seconds
     */
    public Integer getTimeLeftSeconds() {
        return timeLeftSeconds;
    }
}
