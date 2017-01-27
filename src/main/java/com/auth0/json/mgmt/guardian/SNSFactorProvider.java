package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Guardian SNS Factor Provider object. Related to the {@link com.auth0.client.mgmt.GuardianEntity()} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SNSFactorProvider {

    @JsonProperty("aws_access_key_id")
    private String awsAccessKeyId;
    @JsonProperty("aws_secret_access_key")
    private String awsSecretAccessKey;
    @JsonProperty("aws_region")
    private String awsRegion;
    @JsonProperty("sns_apns_platform_application_arn")
    private String snsAPNSPlatformApplicationArn;
    @JsonProperty("sns_gcm_platform_application_arn")
    private String snsGCMPlatformApplicationArn;

    /**
     * Getter for the Amazon Web Services access key id.
     *
     * @return the AWS access key id.
     */
    @JsonProperty("aws_access_key_id")
    public String getAWSAccessKeyId() {
        return awsAccessKeyId;
    }

    /**
     * Setter for the Amazon Web Services access key id.
     *
     * @param awsAccessKeyId the AWS access key id to set.
     */
    @JsonProperty("aws_access_key_id")
    public void setAWSAccessKeyId(String awsAccessKeyId) {
        this.awsAccessKeyId = awsAccessKeyId;
    }

    /**
     * Getter for the Amazon Web Services secret access key.
     *
     * @return the AWS secret access key.
     */
    @JsonProperty("aws_secret_access_key")
    public String getAWSSecretAccessKey() {
        return awsSecretAccessKey;
    }

    /**
     * Setter for the Amazon Web Services secret access key.
     *
     * @param awsSecretAccessKey the AWS secret access key to set.
     */
    @JsonProperty("aws_secret_access_key")
    public void setAWSSecretAccessKey(String awsSecretAccessKey) {
        this.awsSecretAccessKey = awsSecretAccessKey;
    }

    /**
     * Getter for the Amazon Web Services region.
     *
     * @return the AWS region.
     */
    @JsonProperty("aws_region")
    public String getAWSRegion() {
        return awsRegion;
    }

    /**
     * Setter for the Amazon Web Services region.
     *
     * @param awsRegion the AWS region to set.
     */
    @JsonProperty("aws_region")
    public void setAWSRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    /**
     * Getter for the Simple Notification Service Apple Push Notification service platform application Amazon Resource Name.
     *
     * @return the SNS APNs ARN.
     */
    @JsonProperty("sns_apns_platform_application_arn")
    public String getSNSAPNSPlatformApplicationARN() {
        return snsAPNSPlatformApplicationArn;
    }

    /**
     * Setter for the Simple Notification Service Apple Push Notification service platform application Amazon Resource Name.
     *
     * @param apnARN the SNS APNs ARN to set.
     */
    @JsonProperty("sns_apns_platform_application_arn")
    public void setSNSAPNSPlatformApplicationARN(String apnARN) {
        this.snsAPNSPlatformApplicationArn = apnARN;
    }

    /**
     * Getter for the Simple Notification Service Google Cloud Messaging platform application Amazon Resource Name.
     *
     * @return the SNS GCM ARN.
     */
    @JsonProperty("sns_gcm_platform_application_arn")
    public String getSNSGCMPlatformApplicationARN() {
        return snsGCMPlatformApplicationArn;
    }

    /**
     * Setter for the Simple Notification Service Google Cloud Messaging platform application Amazon Resource Name.
     *
     * @param gcmARN the SNS GCM ARN to set.
     */
    @JsonProperty("sns_gcm_platform_application_arn")
    public void setSNSGCMPlatformApplicationARN(String gcmARN) {
        this.snsGCMPlatformApplicationArn = gcmARN;
    }
}
