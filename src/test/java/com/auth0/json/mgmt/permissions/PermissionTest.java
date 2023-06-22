package com.auth0.json.mgmt.permissions;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PermissionTest extends JsonTest<Permission> {

    private static final String json = "{\"permission_name\":\"pName\",\"description\":\"pDesc\",\"resource_server_name\":\"resName\",\"resource_server_identifier\":\"resId\",\"sources\":[{\"source_id\":\"srcId\",\"source_name\":\"srcName\",\"source_type\":\"srcType\"}]}";

    @Test
    public void shouldSerialize() throws Exception {
        Permission permission = new Permission();
        permission.setDescription("pDesc");
        permission.setName("pName");
        permission.setResourceServerId("resServerId");
        permission.setResourceServerName("resServerName");

        String serialized = toJSON(permission);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("description", "pDesc"));
        assertThat(serialized, JsonMatcher.hasEntry("permission_name", "pName"));
        assertThat(serialized, JsonMatcher.hasEntry("resource_server_identifier", "resServerId"));
        assertThat(serialized, JsonMatcher.hasEntry("resource_server_name", "resServerName"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Permission permission = fromJSON(json, Permission.class);

        assertThat(permission, is(notNullValue()));
        assertThat(permission.getDescription(), is("pDesc"));
        assertThat(permission.getName(), is("pName"));
        assertThat(permission.getResourceServerId(), is("resId"));
        assertThat(permission.getResourceServerName(), is("resName"));
        assertThat(permission.getSources(), hasSize(1));

        PermissionSource includedSource = permission.getSources().get(0);
        assertThat(includedSource.getId(), is("srcId"));
        assertThat(includedSource.getName(), is("srcName"));
        assertThat(includedSource.getType(), is("srcType"));
    }

}
