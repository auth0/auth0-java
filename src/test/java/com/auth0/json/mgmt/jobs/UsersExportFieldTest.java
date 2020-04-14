package com.auth0.json.mgmt.jobs;

import com.auth0.json.JsonTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UsersExportFieldTest extends JsonTest<UsersExportField> {

    private static final String json = "{\"name\": \"name_of_the_property\"}";
    private static final String readOnlyJson = "{\"name\": \"name_of_the_property\", \"export_as\": \"new_name_of_the_property\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        UsersExportField field = fromJSON(json, UsersExportField.class);

        assertThat(field, is(notNullValue()));
        assertThat(field.getName(), is("name_of_the_property"));
        assertThat(field.getExportAs(), is(nullValue()));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        UsersExportField field = fromJSON(readOnlyJson, UsersExportField.class);
        MatcherAssert.assertThat(field, Matchers.is(notNullValue()));

        assertThat(field, is(notNullValue()));
        assertThat(field.getName(), is("name_of_the_property"));
        assertThat(field.getExportAs(), is("new_name_of_the_property"));
    }
}
