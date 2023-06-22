package com.auth0.json.mgmt.jobs;

import com.auth0.json.JsonTest;
import com.auth0.json.mgmt.users.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JobErrorDetailsTest extends JsonTest<JobErrorDetails> {

    String json = "{\"user\":{\"email\":\"john.doe@gmail.com\"},\"errors\":[{\"code\":\"code\",\"message\":\"message\",\"path\":\"path\"}]}";

    @Test
    public void shouldDeserialize() throws Exception {
        JobErrorDetails errorDetails = fromJSON(json, JobErrorDetails.class);

        assertThat(errorDetails, is(notNullValue()));
        assertThat(errorDetails.getUser(), is(notNullValue()));
        assertThat(errorDetails.getUser().getEmail(), is(equalTo("john.doe@gmail.com")));
        assertThat(errorDetails.getErrors(), hasSize(1));

        assertThat(errorDetails.getErrors().get(0).getCode(), is(equalTo("code")));
        assertThat(errorDetails.getErrors().get(0).getMessage(), is(equalTo("message")));
        assertThat(errorDetails.getErrors().get(0).getPath(), is(equalTo("path")));
    }

    @Test
    public void shouldSerialize() throws JsonProcessingException {
        User user = new User();
        user.setEmail("john.doe@gmail.com");
        JobError error = new JobError("code", "message", "path");
        JobErrorDetails errorDetails = new JobErrorDetails(user, Collections.singletonList(error));
        String serialized = toJSON(errorDetails);

        assertThat(serialized, is(equalTo(json)));
    }
}
