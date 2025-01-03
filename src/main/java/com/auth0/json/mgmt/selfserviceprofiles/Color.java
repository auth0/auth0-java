package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Color {
    @JsonProperty("primary")
    private String primary;

    /**
     * Creates a new instance of the Color class.
     */
    public Color() {}

    /**
     * Creates a new instance of the Color class.
     * @param primary the primary color.
     */
    @JsonCreator
    public Color(@JsonProperty("primary") String primary) {
        this.primary = primary;
    }

    /**
     * Getter for the primary color.
     * @return the primary color.
     */
    public String getPrimary() {
        return primary;
    }

    /**
     * Setter for the primary color.
     * @param primary the primary color to set.
     */
    public void setPrimary(String primary) {
        this.primary = primary;
    }
}
