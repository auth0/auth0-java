package com.auth0.json.mgmt.userAttributeProfiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OidcMapping {
    @JsonProperty("mapping")
    private String mapping;
    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("mapping")
    public String getMapping() {
        return mapping;
    }

    @JsonProperty("mapping")
    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    @JsonProperty("display_name")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
