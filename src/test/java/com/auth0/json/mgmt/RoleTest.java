package com.auth0.json.mgmt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

public class RoleTest extends JsonTest<Role> {

    private static final String json = "{\"name\":\"role\",\"description\":\"desc\"}";
    private static final String readOnlyJson = "{\"id\":\"roleId\"}";

    @Test
    public void shouldSerialize() throws Exception {
        Role role = new Role();
        role.setName("role");
        role.setDescription("desc");

        String serialized = toJSON(role);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("name", "role"));
        assertThat(serialized, JsonMatcher.hasEntry("description", "desc"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Role role = fromJSON(json, Role.class);

        assertThat(role, is(notNullValue()));
        assertThat(role.getName(), is("role"));
        assertThat(role.getDescription(), is("desc"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        Role role = fromJSON(readOnlyJson, Role.class);
        assertThat(role, is(notNullValue()));

        assertThat(role.getId(), is("roleId"));
    }
}
