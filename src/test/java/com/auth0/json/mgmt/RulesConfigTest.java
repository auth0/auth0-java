package com.auth0.json.mgmt;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RulesConfigTest extends JsonTest<RulesConfig> {

    private static final String json = "{\"key\":\"my-key\",\"value\":\"SECRET\"}";
    private static final String readOnlyJson = "{\"id\":\"ruleId\",\"stage\":\"login_success\"}";

    @Test
    public void shouldSerialize() throws Exception {
        RulesConfig rule = new RulesConfig("SECRET");

        String serialized = toJSON(rule);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("value", "SECRET"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        RulesConfig rule = fromJSON(json, RulesConfig.class);

        assertThat(rule, is(notNullValue()));
        assertThat(rule.getValue(), is("SECRET"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        RulesConfig rule = fromJSON(readOnlyJson, RulesConfig.class);
        assertThat(rule, is(notNullValue()));

        assertThat(rule.getKey(), is("my-key"));
        assertThat(rule.getValue(), is("SECRET"));
    }
}