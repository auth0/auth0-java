package com.auth0.json.mgmt.jobs;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JobTest extends JsonTest<Job> {

    private static final String json = "{\"status\": \"completed\",\"type\": \"verification_email\",\"created_at\": \"2016-02-23T19:57:29.532Z\",\"id\": \"job_0000000000000001\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        Job job = fromJSON(json, Job.class);

        assertThat(job, is(notNullValue()));
        assertThat(job.getId(), is("job_0000000000000001"));
        assertThat(job.getStatus(), is("completed"));
        assertThat(job.getType(), is("verification_email"));
        assertThat(job.getCreatedAt(), is(parseJSONDate("2016-02-23T19:57:29.532Z")));
    }

    @Test
    public void shouldSetValues() throws Exception {
        Job job = fromJSON(json, Job.class);
        job.setLocation("https://test.auth0.com/mock_endpoint.gz");
        job.setPercentageDone(100);
        job.setTimeLeftSeconds(0);

        assertThat(job, is(notNullValue()));
        assertThat(job.getId(), is("job_0000000000000001"));
        assertThat(job.getStatus(), is("completed"));
        assertThat(job.getType(), is("verification_email"));
        assertThat(job.getCreatedAt(), is(parseJSONDate("2016-02-23T19:57:29.532Z")));
        assertThat(job.getLocation(), is("https://test.auth0.com/mock_endpoint.gz"));
        assertThat(job.getPercentageDone(), is(100));
        assertThat(job.getTimeLeftSeconds(), is(0));
    }
}
