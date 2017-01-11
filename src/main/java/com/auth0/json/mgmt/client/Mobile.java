package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mobile {

    private Android android;
    private IOS ios;

    @JsonCreator
    public Mobile(@JsonProperty("android") Android android, @JsonProperty("ios") IOS ios) {
        this.android = android;
        this.ios = ios;
    }

    @JsonProperty("android")
    public Android getAndroid() {
        return android;
    }

    @JsonProperty("android")
    public void setAndroid(Android android) {
        this.android = android;
    }

    @JsonProperty("ios")
    public IOS getIos() {
        return ios;
    }

    @JsonProperty("ios")
    public void setIos(IOS ios) {
        this.ios = ios;
    }
}