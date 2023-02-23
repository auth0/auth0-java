package com.auth0.json.mgmt.permissions;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PermissionSourceTest extends JsonTest<PermissionSource> {

    private static final String json = "{\"source_name\":\"sName\",\"source_id\":\"sId\",\"source_type\":\"sType\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        PermissionSource permission = fromJSON(json, PermissionSource.class);

        assertThat(permission, is(notNullValue()));
        assertThat(permission.getName(), is("sName"));
        assertThat(permission.getId(), is("sId"));
        assertThat(permission.getType(), is("sType"));
    }

}
