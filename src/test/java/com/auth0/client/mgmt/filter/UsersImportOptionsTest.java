package com.auth0.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UsersImportOptionsTest {
    private UsersImportOptions options;

    @BeforeEach
    public void setUp() {
        options = new UsersImportOptions();
    }

    @Test
    public void shouldSetExternalId() {
        UsersImportOptions instance = options.withExternalId("ext_id123");

        assertThat(options, is(instance));
        assertThat(options.getAsMap(), is(notNullValue()));
        assertThat(options.getAsMap(), Matchers.hasEntry("external_id", "ext_id123"));
    }

    @Test
    public void shouldSetSendCompletionEmail() {
        UsersImportOptions instance = options.withSendCompletionEmail(false);

        assertThat(options, is(instance));
        assertThat(options.getAsMap(), is(notNullValue()));
        assertThat(options.getAsMap(), Matchers.hasEntry("send_completion_email", false));
    }

    @Test
    public void shouldSetUpsert() {
        UsersImportOptions instance = options.withUpsert(true);

        assertThat(options, is(instance));
        assertThat(options.getAsMap(), is(notNullValue()));
        assertThat(options.getAsMap(), Matchers.hasEntry("upsert", true));
    }

    @Test
    public void shouldSetConnectionId() {
        UsersImportOptions instance = options.withConnectionId("123");

        assertThat(options, is(instance));
        assertThat(options.getAsMap(), is(notNullValue()));
        assertThat(options.getAsMap(), Matchers.hasEntry("connection_id", "123"));
    }
}
