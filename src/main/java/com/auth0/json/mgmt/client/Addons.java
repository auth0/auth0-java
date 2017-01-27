package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "unused"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Addons {

    @JsonProperty("rms")
    private Addon rms;
    @JsonProperty("mscrm")
    private Addon mscrm;
    @JsonProperty("slack")
    private Addon slack;
    @JsonProperty("layer")
    private Addon layer;

    @JsonUnwrapped
    @JsonIgnore
    private Map<String, Addon> additionalAddons;

    @JsonCreator
    public Addons(@JsonProperty("rms") Addon rms, @JsonProperty("mscrm") Addon mscrm, @JsonProperty("slack") Addon slack, @JsonProperty("layer") Addon layer) {
        this.additionalAddons = new HashMap<>();
        this.rms = rms;
        this.mscrm = mscrm;
        this.slack = slack;
        this.layer = layer;
    }

    @JsonProperty("rms")
    public Addon getRMS() {
        return rms;
    }

    @JsonProperty("rms")
    public void setRMS(Addon rms) {
        this.rms = rms;
    }

    @JsonProperty("mscrm")
    public Addon getMSCRM() {
        return mscrm;
    }

    @JsonProperty("mscrm")
    public void setMSCRM(Addon mscrm) {
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
    public void setAdditionalAddon(String name, Addon addon) {
        this.additionalAddons.put(name, addon);
    }
}
