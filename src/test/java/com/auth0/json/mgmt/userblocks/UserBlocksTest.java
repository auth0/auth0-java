package com.auth0.json.mgmt.userblocks;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserBlocksTest extends JsonTest<UserBlocks> {

    private static final String json = "{\"blocked_for\":[{},{}]}";

    @Test
    public void shouldDeserialize() throws Exception {
        UserBlocks blocks = fromJSON(json, UserBlocks.class);

        assertThat(blocks, is(notNullValue()));
        assertThat(blocks.getBlockedFor(), is(notNullValue()));
        assertThat(blocks.getBlockedFor(), hasSize(2));
    }
}