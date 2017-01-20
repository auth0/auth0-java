package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuardianSnsFactorProvider {

    @JsonProperty("aws_access_key_id")
    private String awsAccessKeyId;
    @JsonProperty("aws_secret_access_key")
    private String awsSecretAccessKey;
    @JsonProperty("aws_region")
    private String awsRegion;
    @JsonProperty("sns_apns_platform_application_arn")
    private String snsApnsPlatformApplicationArn;
    @JsonProperty("sns_gcm_platform_application_arn")
    private String snsGcmPlatformApplicationArn;

    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    public void setAwsAccessKeyId(String awsAccessKeyId) {
        this.awsAccessKeyId = awsAccessKeyId;
    }

    public String getAwsSecretAccessKey() {
        return awsSecretAccessKey;
    }

    public void setAwsSecretAccessKey(String awsSecretAccessKey) {
        this.awsSecretAccessKey = awsSecretAccessKey;
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public String getSnsApnsPlatformApplicationArn() {
        return snsApnsPlatformApplicationArn;
    }

    public void setSnsApnsPlatformApplicationArn(String snsApnsPlatformApplicationArn) {
        this.snsApnsPlatformApplicationArn = snsApnsPlatformApplicationArn;
    }

    public String getSnsGcmPlatformApplicationArn() {
        return snsGcmPlatformApplicationArn;
    }

    public void setSnsGcmPlatformApplicationArn(String snsGcmPlatformApplicationArn) {
        this.snsGcmPlatformApplicationArn = snsGcmPlatformApplicationArn;
    }
}
