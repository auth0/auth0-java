package com.auth0.json.mgmt.guardian;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SNSFactorProviderTest extends JsonTest<SNSFactorProvider> {

    private static final String json = "{\"aws_access_key_id\":\"akey\",\"aws_secret_access_key\":\"secretakey\",\"aws_region\":\"ar\",\"sns_apns_platform_application_arn\":\"arn1\",\"sns_gcm_platform_application_arn\":\"arn2\"}";

    @Test
    public void shouldSerialize() throws Exception {
        SNSFactorProvider provider = new SNSFactorProvider();
        provider.setAWSRegion("ar");
        provider.setAWSAccessKeyId("akey");
        provider.setAWSSecretAccessKey("secretakey");
        provider.setSNSAPNSPlatformApplicationARN("arn1");
        provider.setSNSGCMPlatformApplicationARN("arn2");

        String serialized = toJSON(provider);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("aws_access_key_id", "akey"));
        assertThat(serialized, JsonMatcher.hasEntry("aws_secret_access_key", "secretakey"));
        assertThat(serialized, JsonMatcher.hasEntry("aws_region", "ar"));
        assertThat(serialized, JsonMatcher.hasEntry("sns_apns_platform_application_arn", "arn1"));
        assertThat(serialized, JsonMatcher.hasEntry("sns_gcm_platform_application_arn", "arn2"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        SNSFactorProvider provider = fromJSON(json, SNSFactorProvider.class);

        assertThat(provider, is(notNullValue()));
        assertThat(provider.getAWSRegion(), is("ar"));
        assertThat(provider.getAWSAccessKeyId(), is("akey"));
        assertThat(provider.getAWSSecretAccessKey(), is("secretakey"));
        assertThat(provider.getSNSAPNSPlatformApplicationARN(), is("arn1"));
        assertThat(provider.getSNSGCMPlatformApplicationARN(), is("arn2"));
    }

}