package com.auth0.json.mgmt;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import com.auth0.json.mgmt.resourceserver.Scope;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ScopeTest extends JsonTest<Scope> {
    private static final String SCOPE_JSON = "src/test/resources/mgmt/scope.json";

    @Test
    public void serialize() throws Exception {
        Scope scope = new Scope("read:client_grants");
        scope.setDescription("Read Client Grants");
        String json = toJSON(scope);

        assertThat(json, JsonMatcher.hasEntry("value", "read:client_grants"));
        assertThat(json, JsonMatcher.hasEntry("description", "Read Client Grants"));
    }

    @Test
    public void deserialize() throws Exception {
        Scope deserialized = fromJSON(readTextFile(SCOPE_JSON), Scope.class);

        assertThat(deserialized.getValue(), is("read:client_grants"));
        assertThat(deserialized.getDescription(), is("Read Client Grants"));
    }
}
