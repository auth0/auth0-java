package com.auth0.json.mgmt.jobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents the summary of a completed Auth0 Job object. Related to the {@link com.auth0.client.mgmt.JobsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobSummary {

    @JsonProperty("failed")
    private Integer failed;
    @JsonProperty("updated")
    private Integer updated;
    @JsonProperty("inserted")
    private Integer inserted;
    @JsonProperty("total")
    private Integer total;

    @JsonCreator
    private JobSummary(@JsonProperty("failed") Integer failed, @JsonProperty("updated") Integer updated, @JsonProperty("inserted") Integer inserted, @JsonProperty("total") Integer total) {
        this.failed = failed;
        this.updated = updated;
        this.inserted = inserted;
        this.total = total;
    }

    @JsonProperty("failed")
    public Integer getFailed() {
        return failed;
    }

    @JsonProperty("updated")
    public Integer getUpdated() {
        return updated;
    }

    @JsonProperty("inserted")
    public Integer getInserted() {
        return inserted;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }
}
