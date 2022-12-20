package com.auth0.json.mgmt.permissions;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PermissionsPageTest extends JsonTest<PermissionsPage> {
    private static final String jsonWithTotals = "{\"start\":0,\"length\":10,\"total\":14,\"limit\":50,\"permissions\":[{\"permission_name\":\"pName\",\"description\":\"pDesc\",\"resource_server_name\":\"resName\",\"resource_server_identifier\":\"resId\",\"sources\":[]}]}";
    private static final String jsonWithoutTotals = "[{\"permission_name\":\"pName\",\"description\":\"pDesc\",\"resource_server_name\":\"resName\",\"resource_server_identifier\":\"resId\",\"sources\":[]}]";

    @Test
    public void shouldDeserializeWithoutTotals() throws Exception {
        PermissionsPage page = fromJSON(jsonWithoutTotals, PermissionsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(nullValue()));
        assertThat(page.getLength(), is(nullValue()));
        assertThat(page.getTotal(), is(nullValue()));
        assertThat(page.getLimit(), is(nullValue()));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(1));
    }

    @Test
    public void shouldDeserializeWithTotals() throws Exception {
        PermissionsPage page = fromJSON(jsonWithTotals, PermissionsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(0));
        assertThat(page.getLength(), is(10));
        assertThat(page.getTotal(), is(14));
        assertThat(page.getLimit(), is(50));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(1));
    }

}
