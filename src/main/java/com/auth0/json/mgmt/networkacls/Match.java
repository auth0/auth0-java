package com.auth0.json.mgmt.networkacls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Match {
    private List<Integer> asns;
    @JsonProperty("geo_country_codes")
    private List<String> geoCountryCodes;
    @JsonProperty("geo_subdivision_codes")
    private List<String> geoSubdivisionCodes;
    @JsonProperty("ipv4_cidrs")
    private List<String> ipv4Cidrs;
    @JsonProperty("ipv6_cidrs")
    private List<String> ipv6Cidrs;
    @JsonProperty("ja3_fingerprints")
    private List<String> ja3Fingerprints;
    @JsonProperty("ja4_fingerprints")
    private List<String> ja4Fingerprints;
    @JsonProperty("user_agents")
    private List<String> userAgents;

    /**
     * Getter for the ASNs.
     * @return a list of ASNs.
     */
    public List<Integer> getAsns() {
        return asns;
    }

    /**
     * Setter for the ASNs.
     * @param asns a list of ASNs to set.
     */
    public void setAsns(List<Integer> asns) {
        this.asns = asns;
    }

    /**
     * Getter for the geo country codes.
     * @return a list of geo country codes.
     */
    public List<String> getGeoCountryCodes() {
        return geoCountryCodes;
    }

    /**
     * Setter for the geo country codes.
     * @param geoCountryCodes a list of geo country codes to set.
     */
    public void setGeoCountryCodes(List<String> geoCountryCodes) {
        this.geoCountryCodes = geoCountryCodes;
    }

    /**
     * Getter for the geo subdivision codes.
     * @return a list of geo subdivision codes.
     */
    public List<String> getGeoSubdivisionCodes() {
        return geoSubdivisionCodes;
    }

    /**
     * Setter for the geo subdivision codes.
     * @param geoSubdivisionCodes a list of geo subdivision codes to set.
     */
    public void setGeoSubdivisionCodes(List<String> geoSubdivisionCodes) {
        this.geoSubdivisionCodes = geoSubdivisionCodes;
    }

    /**
     * Getter for the IPv4 CIDRs.
     * @return a list of IPv4 CIDRs.
     */
    public List<String> getIpv4Cidrs() {
        return ipv4Cidrs;
    }

    /**
     * Setter for the IPv4 CIDRs.
     * @param ipv4Cidrs a list of IPv4 CIDRs to set.
     */
    public void setIpv4Cidrs(List<String> ipv4Cidrs) {
        this.ipv4Cidrs = ipv4Cidrs;
    }

    /**
     * Getter for the IPv6 CIDRs.
     * @return a list of IPv6 CIDRs.
     */
    public List<String> getIpv6Cidrs() {
        return ipv6Cidrs;
    }

    /**
     * Setter for the IPv6 CIDRs.
     * @param ipv6Cidrs a list of IPv6 CIDRs to set.
     */
    public void setIpv6Cidrs(List<String> ipv6Cidrs) {
        this.ipv6Cidrs = ipv6Cidrs;
    }

    /**
     * Getter for the JA3 fingerprints.
     * @return a list of JA3 fingerprints.
     */
    public List<String> getJa3Fingerprints() {
        return ja3Fingerprints;
    }

    /**
     * Setter for the JA3 fingerprints.
     * @param ja3Fingerprints a list of JA3 fingerprints to set.
     */
    public void setJa3Fingerprints(List<String> ja3Fingerprints) {
        this.ja3Fingerprints = ja3Fingerprints;
    }

    /**
     * Getter for the JA4 fingerprints.
     * @return a list of JA4 fingerprints.
     */
    public List<String> getJa4Fingerprints() {
        return ja4Fingerprints;
    }

    /**
     * Setter for the JA4 fingerprints.
     * @param ja4Fingerprints a list of JA4 fingerprints to set.
     */
    public void setJa4Fingerprints(List<String> ja4Fingerprints) {
        this.ja4Fingerprints = ja4Fingerprints;
    }

    /**
     * Getter for the user agents.
     * @return a list of user agents.
     */
    public List<String> getUserAgents() {
        return userAgents;
    }

    /**
     * Setter for the user agents.
     * @param userAgents a list of user agents to set.
     */
    public void setUserAgents(List<String> userAgents) {
        this.userAgents = userAgents;
    }
}
