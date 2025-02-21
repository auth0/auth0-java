package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainAliasesConfig {
    @JsonProperty("domain_verification")
    private String domainVerification;

    public DomainAliasesConfig() {}

    /**
     * Creates a new instance of the DomainAliasesConfig class.
     */
    @JsonCreator
    public DomainAliasesConfig(@JsonProperty("domain_verification") String domainVerification) {
        this.domainVerification = domainVerification;
    }

    /**
     * Getter for the domain verification.
     * @return the domain verification.
     */
    public String getDomainVerification() {
        return domainVerification;
    }
}
