package com.auth0.json.mgmt.logevents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationInfo {
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("country_code3")
    private String countryCode3;
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("city_name")
    private String cityName;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("time_zone")
    private String timeZone;
    @JsonProperty("continent_code")
    private String continentCode;

    /**
     * Getter for the country code.
     * @return the country code.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Setter for the country code.
     * @param countryCode the country code to set.
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Getter for the country code 3.
     * @return the country code 3.
     */
    public String getCountryCode3() {
        return countryCode3;
    }

    /**
     * Setter for the country code 3.
     * @param countryCode3 the country code 3 to set.
     */
    public void setCountryCode3(String countryCode3) {
        this.countryCode3 = countryCode3;
    }

    /**
     * Getter for the country name.
     * @return the country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Setter for the country name.
     * @param countryName the country name to set.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Getter for the city name.
     * @return the city name.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Setter for the city name.
     * @param cityName the city name to set.
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Getter for the latitude.
     * @return the latitude.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Setter for the latitude.
     * @param latitude the latitude to set.
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter for the longitude.
     * @return the longitude.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Setter for the longitude.
     * @param longitude the longitude to set.
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter for the time zone.
     * @return the time zone.
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Setter for the time zone.
     * @param timeZone the time zone to set.
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Getter for the continent code.
     * @return the continent code.
     */
    public String getContinentCode() {
        return continentCode;
    }

    /**
     * Setter for the continent code.
     * @param continentCode the continent code to set.
     */
    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }
}
