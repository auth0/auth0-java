package com.auth0.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ObjectMapperProviderTest {

    @Test
    public void providesSameInstance() {
        ObjectMapper mapper = ObjectMapperProvider.getMapper();
        assertThat(mapper, equalTo(ObjectMapperProvider.getMapper()));
    }
}
