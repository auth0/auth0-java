package com.auth0.json.mgmt.emailproviders;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EmailProviderCredentialsTest extends JsonTest<EmailProviderCredentials> {

    private static final String json = "{\"api_key\":\"key123\",\"api_user\":\"username\",\"accessKeyId\":\"id\",\"secretAccessKey\":\"secret\",\"region\":\"ar\",\"smtp_host\":\"host\",\"smtp_port\":1234,\"smtp_user\":\"usr\",\"smtp_pass\":\"pwd\"}";

    @Test
    public void shouldSerialize() throws Exception {
        EmailProviderCredentials credentials = new EmailProviderCredentials("key123");
        credentials.setApiUser("username");
        credentials.setAccessKeyId("id");
        credentials.setSecretAccessKey("secret");
        credentials.setRegion("ar");
        credentials.setSMTPHost("host");
        credentials.setSMTPPort(1234);
        credentials.setSMTPUser("usr");
        credentials.setSMTPPass("pwd");

        String serialized = toJSON(credentials);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("api_key", "key123"));
        assertThat(serialized, JsonMatcher.hasEntry("api_user", "username"));
        assertThat(serialized, JsonMatcher.hasEntry("accessKeyId", "id"));
        assertThat(serialized, JsonMatcher.hasEntry("secretAccessKey", "secret"));
        assertThat(serialized, JsonMatcher.hasEntry("region", "ar"));
        assertThat(serialized, JsonMatcher.hasEntry("smtp_host", "host"));
        assertThat(serialized, JsonMatcher.hasEntry("smtp_port", 1234));
        assertThat(serialized, JsonMatcher.hasEntry("smtp_user", "usr"));
        assertThat(serialized, JsonMatcher.hasEntry("smtp_pass", "pwd"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        EmailProviderCredentials credentials = fromJSON(json, EmailProviderCredentials.class);

        assertThat(credentials, is(notNullValue()));
        assertThat(credentials.getApiKey(), is("key123"));
        assertThat(credentials.getApiUser(), is("username"));
        assertThat(credentials.getAccessKeyId(), is("id"));
        assertThat(credentials.getSecretAccessKey(), is("secret"));
        assertThat(credentials.getRegion(), is("ar"));
        assertThat(credentials.getSMTPHost(), is("host"));
        assertThat(credentials.getSMTPPort(), is(1234));
        assertThat(credentials.getSMTPUser(), is("usr"));
        assertThat(credentials.getSMTPPass(), is("pwd"));
    }
}
