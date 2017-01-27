package com.auth0.json.mgmt;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RuleTest extends JsonTest<Rule> {

    private static final String json = "{\"name\":\"my-rule\",\"script\":\"function(user,context,callback){}\",\"enabled\":true,\"order\":1}";
    private static final String readOnlyJson = "{\"id\":\"ruleId\",\"stage\":\"login_success\"}";

    @Test
    public void shouldSerialize() throws Exception {
        Rule rule = new Rule("my-rule", "function(user,context,callback){}");
        rule.setOrder(1);
        rule.setEnabled(true);

        String serialized = toJSON(rule);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("name", "my-rule"));
        assertThat(serialized, JsonMatcher.hasEntry("script", "function(user,context,callback){}"));
        assertThat(serialized, JsonMatcher.hasEntry("enabled", true));
        assertThat(serialized, JsonMatcher.hasEntry("order", 1));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Rule rule = fromJSON(json, Rule.class);

        assertThat(rule, is(notNullValue()));
        assertThat(rule.getName(), is("my-rule"));
        assertThat(rule.getScript(), is("function(user,context,callback){}"));
        assertThat(rule.getOrder(), is(1));
        assertThat(rule.isEnabled(), is(true));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        Rule rule = fromJSON(readOnlyJson, Rule.class);
        assertThat(rule, is(notNullValue()));

        assertThat(rule.getId(), is("ruleId"));
        assertThat(rule.getStage(), is("login_success"));
    }
}