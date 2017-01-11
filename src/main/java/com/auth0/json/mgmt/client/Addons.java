package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.*;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Addons {

    private Addon rms;
    private Addon mscrm;
    private Addon slack;
    private Addon layer;

    @JsonUnwrapped
    private Map<String, Addon> additionalAddons;

    @JsonCreator
    public Addons(@JsonProperty("rms") Addon rms, @JsonProperty("mscrm") Addon mscrm, @JsonProperty("slack") Addon slack, @JsonProperty("layer") Addon layer) {
        this.rms = rms;
        this.mscrm = mscrm;
        this.slack = slack;
        this.layer = layer;
    }

    @JsonProperty("rms")
    public Addon getRms() {
        return rms;
    }

    @JsonProperty("rms")
    public void setRms(Addon rms) {
        this.rms = rms;
    }

    @JsonProperty("mscrm")
    public Addon getMscrm() {
        return mscrm;
    }

    @JsonProperty("mscrm")
    public void setMscrm(Addon mscrm) {
        this.mscrm = mscrm;
    }

    @JsonProperty("slack")
    public Addon getSlack() {
        return slack;
    }

    @JsonProperty("slack")
    public void setSlack(Addon slack) {
        this.slack = slack;
    }

    @JsonProperty("layer")
    public Addon getLayer() {
        return layer;
    }

    @JsonProperty("layer")
    public void setLayer(Addon layer) {
        this.layer = layer;
    }

    @JsonAnyGetter
    public Map<String, Addon> getAdditionalAddons() {
        return additionalAddons;
    }

    @JsonAnySetter
    public void setAdditionalAddons(Map<String, Addon> additionalAddons) {
        this.additionalAddons = additionalAddons;
    }
}
