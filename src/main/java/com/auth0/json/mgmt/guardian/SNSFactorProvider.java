package com.auth0.json.mgmt.guardian;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents a Guardian's push-notification Factor Provider for Amazon's Simple Notification Service (SNS).
 * Related to the {@link com.auth0.client.mgmt.GuardianEntity} entity.
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
     * Creates a SNS settings object
     *
     * @param awsAccessKeyId the Amazon Web Services access key id.
     * @param awsSecretAccessKey the Amazon Web Services secret access key.
     * @param awsRegion the Amazon Web Services region.
     * @param snsAPNSPlatformApplicationArn the Apple Push Notification Service platform application Amazon Resource Name.
     * @param snsGCMPlatformApplicationArn the Google Cloud Messaging platform application Amazon Resource Name.
     */
    @JsonCreator
    public SNSFactorProvider(@JsonProperty("aws_access_key_id") String awsAccessKeyId, @JsonProperty("aws_secret_access_key") String awsSecretAccessKey, @JsonProperty("aws_region") String awsRegion, @JsonProperty("sns_apns_platform_application_arn") String snsAPNSPlatformApplicationArn, @JsonProperty("sns_gcm_platform_application_arn") String snsGCMPlatformApplicationArn) {
        this.awsAccessKeyId = awsAccessKeyId;
        this.awsSecretAccessKey = awsSecretAccessKey;
        this.awsRegion = awsRegion;
        this.snsAPNSPlatformApplicationArn = snsAPNSPlatformApplicationArn;
        this.snsGCMPlatformApplicationArn = snsGCMPlatformApplicationArn;
    }

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
     * Getter for the Amazon Web Services secret access key.
     *
     * @return the AWS secret access key.
     */
    @JsonProperty("aws_secret_access_key")
    public String getAWSSecretAccessKey() {
        return awsSecretAccessKey;
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
     * Getter for the Simple Notification Service Apple Push Notification service platform application Amazon Resource Name.
     *
     * @return the SNS APNs ARN.
     */
    @JsonProperty("sns_apns_platform_application_arn")
    public String getSNSAPNSPlatformApplicationARN() {
        return snsAPNSPlatformApplicationArn;
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

}
