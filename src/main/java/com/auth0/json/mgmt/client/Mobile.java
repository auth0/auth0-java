package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mobile {

    @JsonProperty("android")
    private Android android;
    @JsonProperty("ios")
    private IOS ios;

    @JsonCreator
    public Mobile(@JsonProperty("android") Android android, @JsonProperty("ios") IOS ios) {
        this.android = android;
        this.ios = ios;
    }

    /**
     * Getter for the android mobile configuration.
     *
     * @return the android mobile configuration.
     */
    @JsonProperty("android")
    public Android getAndroid() {
        return android;
    }

    /**
     * Setter for the Android mobile configuration.
     *
     * @param android the Android mobile configuration to set.
     */
    @JsonProperty("android")
    public void setAndroid(Android android) {
        this.android = android;
    }

    /**
     * Getter for the iOS mobile configuration.
     *
     * @return the iOS mobile configuration.
     */
    @JsonProperty("ios")
    public IOS getIOS() {
        return ios;
    }

    /**
     * Setter for the iOS mobile configuration.
     *
     * @param ios the iOS mobile configuration to set.
     */
    @JsonProperty("ios")
    public void setIOS(IOS ios) {
        this.ios = ios;
    }
}