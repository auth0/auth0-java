package com.auth0.json.mgmt.actions;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BindingsUpdateRequestTest extends JsonTest<BindingsUpdateRequest> {

    @Test
    public void shouldSerialize() throws Exception {
        String json = "{\n" +
            "  \"bindings\": [\n" +
            "    {\n" +
            "      \"ref\": {\n" +
            "        \"type\": \"action_name\",\n" +
            "        \"value\": \"my-action\"\n" +
            "      },\n" +
            "      \"display_name\": \"First Action\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ref\": {\n" +
            "        \"type\": \"action_id\",\n" +
            "        \"value\": \"action-id\"\n" +
            "      },\n" +
            "      \"display_name\": \"Second Action\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        BindingsUpdateRequest body = fromJSON(json, BindingsUpdateRequest.class);
        assertThat(body, is(notNullValue()));
        assertThat(body.getBindingUpdates(), hasSize(2));

        BindingUpdate binding1 = body.getBindingUpdates().get(0);
        assertThat(binding1, is(notNullValue()));
        assertThat(binding1.getDisplayName(),  is("First Action"));
        BindingReference bindingReference1 = binding1.getBindingReference();
        assertThat(bindingReference1, is(notNullValue()));
        assertThat(bindingReference1.getType(), is("action_name"));
        assertThat(bindingReference1.getValue(), is("my-action"));

        BindingUpdate binding2 = body.getBindingUpdates().get(1);
        assertThat(binding2, is(notNullValue()));
        assertThat(binding2.getDisplayName(),  is("Second Action"));
        BindingReference bindingReference2 = binding2.getBindingReference();
        assertThat(bindingReference2, is(notNullValue()));
        assertThat(bindingReference2.getType(), is("action_id"));
        assertThat(bindingReference2.getValue(), is("action-id"));
    }
}
