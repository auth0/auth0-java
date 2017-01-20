package com.auth0.json.mgmt.guardian;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EnrollmentTest extends JsonTest<Enrollment> {

    private static final String json = "{\"id\":\"123\",\"status\":\"confirmed\",\"type\":\"idk\",\"name\":\"auth0\",\"identifier\":\"id123\",\"phone_number\":\"1234567890\", \"auth_method\":\"authenticator\",\"enrolled_at\":\"12:12:12\",\"last_auth\":\"12:12:12\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        Enrollment enrollment = fromJSON(json, Enrollment.class);

        assertThat(enrollment, is(notNullValue()));
        assertThat(enrollment.getId(), is("123"));
        assertThat(enrollment.getStatus(), is("confirmed"));
        assertThat(enrollment.getType(), is("idk"));
        assertThat(enrollment.getName(), is("auth0"));
        assertThat(enrollment.getIdentifier(), is("id123"));
        assertThat(enrollment.getPhoneNumber(), is("1234567890"));
        assertThat(enrollment.getAuthMethod(), is("authenticator"));
        assertThat(enrollment.getEnrolledAt(), is("12:12:12"));
        assertThat(enrollment.getLastAuth(), is("12:12:12"));
    }
}