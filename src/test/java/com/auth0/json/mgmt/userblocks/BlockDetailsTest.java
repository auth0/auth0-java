package com.auth0.json.mgmt.userblocks;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BlockDetailsTest extends JsonTest<BlockDetails> {

    private static final String json = "{\"ip\":\"10.0.0.1\", \"identifier\":\"username\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        BlockDetails details = fromJSON(json, BlockDetails.class);

        assertThat(details, is(notNullValue()));
        assertThat(details.getIdentifier(), is("username"));
        assertThat(details.getIP(), is("10.0.0.1"));
    }
}
