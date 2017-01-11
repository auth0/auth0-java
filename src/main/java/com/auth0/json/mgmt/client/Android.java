package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Android {

    private String appPackageName;
    private List<String> sha256CertFingerprints;

    @JsonCreator
    public Android(@JsonProperty("app_package_name") String appPackageName, @JsonProperty("sha256_cert_fingerprints") List<String> sha256CertFingerprints) {
        this.appPackageName = appPackageName;
        this.sha256CertFingerprints = sha256CertFingerprints;
    }

    @JsonProperty("app_package_name")
    public String getAppPackageName() {
        return appPackageName;
    }

    @JsonProperty("app_package_name")
    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    @JsonProperty("sha256_cert_fingerprints")
    public List<String> getSha256CertFingerprints() {
        return sha256CertFingerprints;
    }

    @JsonProperty("sha256_cert_fingerprints")
    public void setSha256CertFingerprints(List<String> sha256CertFingerprints) {
        this.sha256CertFingerprints = sha256CertFingerprints;
    }
}