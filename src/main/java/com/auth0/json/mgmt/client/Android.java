package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@SuppressWarnings({"WeakerAccess", "unused"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Android {

    @JsonProperty("app_package_name")
    private String appPackageName;
    @JsonProperty("sha256_cert_fingerprints")
    private List<String> sha256CertFingerprints;

    @JsonCreator
    public Android(@JsonProperty("app_package_name") String appPackageName, @JsonProperty("sha256_cert_fingerprints") List<String> sha256CertFingerprints) {
        this.appPackageName = appPackageName;
        this.sha256CertFingerprints = sha256CertFingerprints;
    }

    /**
     * Getter for the Application Package Name, found in the AndroidManifest.xml file.
     *
     * @return the package name.
     */
    @JsonProperty("app_package_name")
    public String getAppPackageName() {
        return appPackageName;
    }

    /**
     * Setter for the Application Package Name.
     *
     * @param appPackageName the package name to set.
     */
    @JsonProperty("app_package_name")
    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    /**
     * Getter for the list of allowed SHA256 certificate fingerprints.
     *
     * @return the list of allowed fingerprints.
     */
    @JsonProperty("sha256_cert_fingerprints")
    public List<String> getSHA256CertFingerprints() {
        return sha256CertFingerprints;
    }

    /**
     * Setter for the list of allowed SHA256 certificate fingerprints.
     *
     * @param certFingerprints the list of allowed fingerprints to set.
     */
    @JsonProperty("sha256_cert_fingerprints")
    public void setSHA256CertFingerprints(List<String> certFingerprints) {
        this.sha256CertFingerprints = certFingerprints;
    }
}