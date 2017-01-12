package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


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

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public List<String> getSha256CertFingerprints() {
        return sha256CertFingerprints;
    }

    public void setSha256CertFingerprints(List<String> sha256CertFingerprints) {
        this.sha256CertFingerprints = sha256CertFingerprints;
    }
}