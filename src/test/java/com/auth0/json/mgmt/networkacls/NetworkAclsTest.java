package com.auth0.json.mgmt.networkacls;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class NetworkAclsTest extends JsonTest<NetworkAcls> {

    private final static String NETWORK_ACLS_JSON = "src/test/resources/mgmt/network_acls.json";

    @Test
    public void deserialize() throws Exception {
        NetworkAcls deserialized = fromJSON(readTextFile(NETWORK_ACLS_JSON), NetworkAcls.class);

        assertThat(deserialized.getDescription(), is("Log America and Canada all the time."));
        assertThat(deserialized.isActive(), is(true));
        assertThat(deserialized.getPriority(), is(1));
        assertThat(deserialized.getId(), is("acl_1"));
        assertThat(deserialized.getCreatedAt(), is("2025-03-17T12:31:57.782Z"));
        assertThat(deserialized.getUpdatedAt(), is("2025-03-17T12:31:57.782Z"));
    }

    @Test
    public void serialize() throws Exception {
        NetworkAcls networkAcls = new NetworkAcls();
        networkAcls.setDescription("Log America and Canada all the time.");
        networkAcls.setActive(true);
        networkAcls.setPriority(1);

        Rule rule = new Rule();
        Match match = new Match();
        match.setGeoCountryCodes(Arrays.asList("US", "CA"));
        rule.setMatch(match);
        rule.setScope("authentication");
        Action action = new Action();
        action.setLog(true);
        rule.setAction(action);

        networkAcls.setRule(rule);

        String json = toJSON(networkAcls);

        assertThat(json, hasEntry("description", "Log America and Canada all the time."));
        assertThat(json, hasEntry("active", true));
        assertThat(json, hasEntry("priority", 1));
        assertThat(json, hasEntry("rule", notNullValue()));
    }
}
