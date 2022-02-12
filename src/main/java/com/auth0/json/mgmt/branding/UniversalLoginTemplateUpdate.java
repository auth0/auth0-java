package com.auth0.json.mgmt.branding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniversalLoginTemplateUpdate {
    @JsonProperty("template")
    private String template;

    /**
     * Getter for the template body.
     *
     * @return the template body.
     */
    @JsonProperty("template")
    public String getTemplate() {
        return template;
    }

    /**
     * Sets for the body of the template.
     */
    @JsonProperty("template")
    public void setTemplate(String template) {
        this.template = template;
    }
}
