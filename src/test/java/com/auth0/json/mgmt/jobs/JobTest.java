package com.auth0.json.mgmt.jobs;

import com.auth0.json.JsonTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JobTest extends JsonTest<Job> {

    private static final String json = "{\"status\": \"completed\",\"type\": \"verification_email\",\"created_at\": \"2016-02-23T19:57:29.532Z\",\"id\": \"job_0000000000000001\"}";
    private static final String readOnlyJson = "{\"status\": \"completed\",\"type\": \"verification_email\",\"created_at\": \"2016-02-23T19:57:29.532Z\",\"id\": \"job_0000000000000001\", \"connection_id\": \"conn_1\", \"connection\": \"Custom-DB-Connection\", \"format\": \"csv\", \"location\": \"https://auth0.com/jobs/\", \"percentage_done\": 89, \"time_left_seconds\": 9814, \"summary\":{\"failed\":2,\"updated\":1,\"inserted\":3,\"total\":6}, \"external_id\": \"ext_id123\"}";

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
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        Job job = fromJSON(readOnlyJson, Job.class);
        MatcherAssert.assertThat(job, Matchers.is(notNullValue()));

        assertThat(job, is(notNullValue()));
        assertThat(job.getId(), is("job_0000000000000001"));
        assertThat(job.getStatus(), is("completed"));
        assertThat(job.getType(), is("verification_email"));
        assertThat(job.getCreatedAt(), is(parseJSONDate("2016-02-23T19:57:29.532Z")));
        assertThat(job.getFormat(), is("csv"));
        assertThat(job.getConnection(), is("Custom-DB-Connection"));
        assertThat(job.getConnectionId(), is("conn_1"));
        assertThat(job.getLocation(), is("https://auth0.com/jobs/"));
        assertThat(job.getPercentageDone(), is(89));
        assertThat(job.getTimeLeftSeconds(), is(9814));
        assertThat(job.getExternalId(), is("ext_id123"));
        assertThat(job.getSummary().getFailed(), is(2));
        assertThat(job.getSummary().getUpdated(), is(1));
        assertThat(job.getSummary().getInserted(), is(3));
        assertThat(job.getSummary().getTotal(), is(6));
    }
}
