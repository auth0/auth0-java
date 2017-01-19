package com.auth0.json.mgmt;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GuardianSnsFactorProviderTest extends JsonTest<GuardianSnsFactorProvider> {

    private static final String json = "{\"aws_access_key_id\":\"akey\",\"aws_secret_access_key\":\"secretakey\",\"aws_region\":\"ar\",\"sns_apns_platform_application_arn\":\"arn1\",\"sns_gcm_platform_application_arn\":\"arn2\"}";

    @Test
    public void shouldSerialize() throws Exception {
        GuardianSnsFactorProvider provider = new GuardianSnsFactorProvider();
        provider.setAwsRegion("ar");
        provider.setAwsAccessKeyId("akey");
        provider.setAwsSecretAccessKey("secretakey");
        provider.setSnsApnsPlatformApplicationArn("arn1");
        provider.setSnsGcmPlatformApplicationArn("arn2");

        String serialized = toJSON(provider);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, is(equalTo(json)));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        GuardianSnsFactorProvider provider = fromJSON(json, GuardianSnsFactorProvider.class);

        assertThat(provider, is(notNullValue()));
        assertThat(provider.getAwsRegion(), is("ar"));
        assertThat(provider.getAwsAccessKeyId(), is("akey"));
        assertThat(provider.getAwsSecretAccessKey(), is("secretakey"));
        assertThat(provider.getSnsApnsPlatformApplicationArn(), is("arn1"));
        assertThat(provider.getSnsGcmPlatformApplicationArn(), is("arn2"));
    }

}