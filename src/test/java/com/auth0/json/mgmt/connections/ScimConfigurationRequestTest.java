package com.auth0.json.mgmt.connections;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ScimConfigurationRequestTest extends JsonTest<ScimConfigurationRequest> {

    private final static String json = "{\n" +
        "  \"user_id_attribute\": \"externalId\",\n" +
        "  \"mapping\": [\n" +
        "    {\n" +
        "      \"auth0\": \"preferred_username\",\n" +
        "      \"scim\": \"userName\"\n" +
        "    }\n" +
        "  ]\n" +
        "}";

    @Test
    public void serialize() throws Exception {
        ScimConfigurationRequest request = new ScimConfigurationRequest();
        request.setUserIdAttribute("externalId");
        List<Mapping> mappingList = new ArrayList<>();
        Mapping mapping = new Mapping("preferred_username", "userName");
        mappingList.add(mapping);
        request.setMapping(mappingList);

        String serialized = toJSON(request);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id_attribute", "externalId"));
        assertThat(serialized, JsonMatcher.hasEntry("mapping", notNullValue()));
        assertThat(serialized, containsString("\"mapping\":[{\"auth0\":\"preferred_username\",\"scim\":\"userName\"}]"));
    }

    @Test
    public void deserialize() throws Exception {
        ScimConfigurationRequest deserialized = fromJSON(json, ScimConfigurationRequest.class);

        assertThat(deserialized, is(notNullValue()));

        assertThat(deserialized.getUserIdAttribute(), is("externalId"));
        assertThat(deserialized.getMapping().get(0).getAuth0(), is("preferred_username"));
        assertThat(deserialized.getMapping().get(0).getScim(), is("userName"));
    }
}
