package com.auth0.client.mgmt.filter;

import com.auth0.json.mgmt.jobs.UsersExportField;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class UsersExportFilterTest {

    private UsersExportFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new UsersExportFilter();
    }

    @Test
    public void shouldFilterByLimit() {
        UsersExportFilter instance = filter.withLimit(123);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("limit", 123));
    }

    @Test
    public void shouldFilterByFormat() {
        UsersExportFilter instance = filter.withFormat("json");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("format", "json"));
    }

    @Test
    public void shouldFilterByFields() {
        ArrayList<UsersExportField> fields = new ArrayList<>();
        fields.add(new UsersExportField("first_name"));
        fields.add(new UsersExportField("last_name"));
        fields.add(new UsersExportField("metadata.custom_property", "custom"));
        filter.withFields(fields);

        UsersExportFilter instance = filter.withFields(fields);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasKey("fields"));

        @SuppressWarnings("unchecked")
        List<UsersExportField> bodyFields = (List<UsersExportField>) filter.getAsMap().get("fields");
        assertThat(bodyFields.get(0).getName(), is("first_name"));
        assertThat(bodyFields.get(0).getExportAs(), is(nullValue()));
        assertThat(bodyFields.get(1).getName(), is("last_name"));
        assertThat(bodyFields.get(1).getExportAs(), is(nullValue()));
        assertThat(bodyFields.get(2).getName(), is("metadata.custom_property"));
        assertThat(bodyFields.get(2).getExportAs(), is("custom"));
    }

}