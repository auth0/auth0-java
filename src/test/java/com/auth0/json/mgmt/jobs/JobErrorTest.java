package com.auth0.json.mgmt.jobs;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JobErrorTest extends JsonTest<JobError> {

    private static final String json = "{\"statusCode\": 401, \"message\": \"Missing authentication\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        JobError job = fromJSON(json, JobError.class);

        assertThat(job, is(notNullValue()));
        assertThat(job.getStatusCode(), is(401));
        assertThat(job.getMessage(), is("Missing authentication"));
    }
}
