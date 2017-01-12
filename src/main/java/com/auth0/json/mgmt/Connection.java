package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Connection {

    @JsonProperty("name")
    private String name;
    @JsonProperty("options")
    private Map<String, Object> options;
    @JsonProperty("id")
    private String id;
    @JsonProperty("strategy")
    private String strategy;
    @JsonProperty("enabled_clients")
    private List<String> enabledClients = null;

    @JsonCreator
    public Connection(@JsonProperty("name") String name, @JsonProperty("strategy") String strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public List<String> getEnabledClients() {
        return enabledClients;
    }

    public void setEnabledClients(List<String> enabledClients) {
        this.enabledClients = enabledClients;
    }

}