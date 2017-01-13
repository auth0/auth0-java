package com.auth0.json.mgmt;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LogEventsPageTest extends JsonTest<LogEventsPage>{
    private static final String jsonWithTotals = "{\"start\":0,\"length\":10,\"total\":14,\"limit\":50,\"logs\":[{\"date\":\"2017-01-13T17:05:03.382Z\",\"type\":\"s\",\"connection\":\"Username-Password-Authentication\",\"connection_id\":\"con_0l1cXsZE7Ati0y\",\"client_id\":\"Owu62gnGsRYhk1v9SfB3IJcRIze\",\"client_name\":\"Auth0\",\"ip\":\"181.008.186.8\",\"user_agent\":\"Chrome 55.0.2883 / Mac OS X 10.12.2\",\"details\":{\"stats\":{\"loginsCount\":368}},\"user_id\":\"auth0|789789\",\"user_name\":\"asdasd\",\"strategy\":\"auth0\",\"strategy_type\":\"database\",\"_id\":\"7878787878978798789\",\"isMobile\":false}]}";
    private static final String jsonWithoutTotals = "[{\"date\":\"2017-01-13T17:05:03.382Z\",\"type\":\"s\",\"connection\":\"Username-Password-Authentication\",\"connection_id\":\"con_0l1cXsZE7Ati0yrT\",\"client_id\":\"Owu62gnGsRYhk1v9SfB3c6IUbIJcRIze\",\"client_name\":\"Auth0.Android/LockDemo\",\"ip\":\"181.47.186.202\",\"user_agent\":\"Chrome 55.0.2883 / Mac OS X 10.12.2\",\"details\":{\"stats\":{\"loginsCount\":368}},\"user_id\":\"auth0|5751d11a85a56dd86c460726\",\"user_name\":\"asdasd\",\"strategy\":\"auth0\",\"strategy_type\":\"database\",\"_id\":\"49560429270835143462013614719763219271357328593426317314\",\"isMobile\":false}]";

    @Test
    public void shouldDeserializeWithoutTotals() throws Exception {
        LogEventsPage page = fromJSON(jsonWithoutTotals, LogEventsPage.class);

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
        LogEventsPage page = fromJSON(jsonWithTotals, LogEventsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(0));
        assertThat(page.getLength(), is(10));
        assertThat(page.getTotal(), is(14));
        assertThat(page.getLimit(), is(50));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(1));
    }

}