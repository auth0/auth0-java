package com.auth0.json.mgmt.branding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniversalLoginTemplate {
    @JsonProperty("body")
    private String body;

    /**
     * Getter for the template body.
     *
     * @return the template body.
     */
    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    /**
     * Sets for the body of the template.
     */
    @JsonProperty("body")
    public void setBody(String body) {
        this.body = body;
    }
}
