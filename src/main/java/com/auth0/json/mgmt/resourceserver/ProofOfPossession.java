package com.auth0.json.mgmt.resourceserver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProofOfPossession {

    @JsonProperty("mechanism")
    private String mechanism;
    @JsonProperty("required")
    private Boolean required;

    @JsonCreator
    public ProofOfPossession(@JsonProperty("mechanism") String mechanism, @JsonProperty("required") Boolean required) {
        this.mechanism = mechanism;
        this.required = required;
    }

    /**
     * Getter for the mechanism of the Proof of Possession.
     * @return the mechanism of the Proof of Possession.
     */
    public String getMechanism() {
        return mechanism;
    }

    /**
     * Setter for the mechanism of the Proof of Possession.
     * @param mechanism the mechanism of the Proof of Possession.
     */
    public void setMechanism(String mechanism) {
        this.mechanism = mechanism;
    }

    /**
     * Getter for the required flag of the Proof of Possession.
     * @return the required flag of the Proof of Possession.
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * Setter for the required flag of the Proof of Possession.
     * @param required the required flag of the Proof of Possession.
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }
}
