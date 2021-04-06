package com.auth0.json.mgmt.organizations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the request body when adding or deleting members from an organization.
 * @see com.auth0.client.mgmt.OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Members {

    @JsonProperty("members")
    private List<String> members;

    /**
     * Create a new instance.
     *
     * @param members a list of {@linkplain Member}
     */
    @JsonCreator
    public Members(@JsonProperty("members") List<String> members) {
        this.members = members;
    }

    /**
     * @return the list of members.
     */
    public List<String> getMembers() {
        return members;
    }

}
