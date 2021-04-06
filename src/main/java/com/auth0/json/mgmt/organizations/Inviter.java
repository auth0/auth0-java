package com.auth0.json.mgmt.organizations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the Inviter object for an Invitation.
 * @see Invitation
 * @see com.auth0.client.mgmt.OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Inviter {

    @JsonProperty("name")
    private String name;

    /**
     * Create a new instance.
     *
     * @param name the name of the Inviter.
     */
    public Inviter(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * @return the name of this Inviter.
     */
    public String getName() {
        return name;
    }
}
