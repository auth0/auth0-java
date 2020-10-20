package com.auth0.client.auth.options;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class CustomHeaderOptionsTest
{
    @Test
    public void shouldGetNewInstance() {
        CustomHeaderOptions instance = new CustomHeaderOptions();
        assertThat(instance, is(notNullValue()));
    }

    @Test
    public void shouldGetNewInstanceWithAuth0ForwardedForValueSet() {
        CustomHeaderOptions instance = new CustomHeaderOptions().withAuth0ForwardedForHeader("127.0.0.1");
        assertThat(instance, is(notNullValue()));
        assertThat(instance.getAsMap().containsKey(CustomHeaderOptions.AUTH0_FORWARDED_FOR_HEADER), is(true));
        assertThat(instance.getAsMap().get(CustomHeaderOptions.AUTH0_FORWARDED_FOR_HEADER), is("127.0.0.1"));
    }

}
