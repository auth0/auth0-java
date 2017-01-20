package com.auth0.json.mgmt.users;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UsersPageTest extends JsonTest<UsersPage> {
    private static final String jsonWithTotals = "{\"start\":0,\"length\":10,\"total\":14,\"limit\":50,\"users\":[{\"connection\":\"auth0\",\"client_id\":\"client123\",\"password\":\"pwd\",\"verify_password\":true,\"username\":\"usr\",\"email\":\"me@auth0.com\",\"email_verified\":true,\"verify_email\":true,\"phone_number\":\"1234567890\",\"phone_verified\":true,\"verify_phone_number\":true,\"picture\":\"https://pic.ture/12\",\"name\":\"John\",\"nickname\":\"Johny\",\"given_name\":\"John\",\"family_name\":\"Walker\",\"created_at\":\"12:12:12\",\"updated_at\":\"12:12:12\",\"identities\":[],\"app_metadata\":{},\"user_metadata\":{},\"last_ip\":\"10.0.0.1\",\"last_login\":\"12:12:12\",\"logins_count\":10,\"blocked\":true}]}";
    private static final String jsonWithoutTotals = "[{\"connection\":\"auth0\",\"client_id\":\"client123\",\"password\":\"pwd\",\"verify_password\":true,\"username\":\"usr\",\"email\":\"me@auth0.com\",\"email_verified\":true,\"verify_email\":true,\"phone_number\":\"1234567890\",\"phone_verified\":true,\"verify_phone_number\":true,\"picture\":\"https://pic.ture/12\",\"name\":\"John\",\"nickname\":\"Johny\",\"given_name\":\"John\",\"family_name\":\"Walker\",\"created_at\":\"12:12:12\",\"updated_at\":\"12:12:12\",\"identities\":[],\"app_metadata\":{},\"user_metadata\":{},\"last_ip\":\"10.0.0.1\",\"last_login\":\"12:12:12\",\"logins_count\":10,\"blocked\":true}]";

    @Test
    public void shouldDeserializeWithoutTotals() throws Exception {
        UsersPage page = fromJSON(jsonWithoutTotals, UsersPage.class);

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
        UsersPage page = fromJSON(jsonWithTotals, UsersPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(0));
        assertThat(page.getLength(), is(10));
        assertThat(page.getTotal(), is(14));
        assertThat(page.getLimit(), is(50));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(1));
    }

}