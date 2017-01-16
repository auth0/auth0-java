package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rule {
    @JsonProperty("name")
    private String name;
    @JsonProperty("script")
    private String script;
    @JsonProperty("id")
    private String id;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("order")
    private Integer order;
    @JsonProperty("stage")
    private String stage;

    @JsonCreator
    public Rule(@JsonProperty("name") String name, @JsonProperty("script") String script) {
        this.name = name;
        this.script = script;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getId() {
        return id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getStage() {
        return stage;
    }
}
