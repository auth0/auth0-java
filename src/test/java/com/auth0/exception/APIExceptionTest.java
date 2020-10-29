package com.auth0.exception;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class APIExceptionTest {

    private Map<String, Object> values;

    @Before
    public void setUp() {
        values = new HashMap<>();
    }

    @Test
    public void shouldBeMfaRequiredWithMfaTokenError() {
        values.put("error", "mfa_required");
        values.put("mfa_token", "some-mfa-token");
        APIException apiException = new APIException(values, 42);
        assertThat(apiException.isMultifactorRequired(), is(true));
        assertThat(apiException.getValue("mfa_token"), is("some-mfa-token"));
    }

    @Test
    public void shouldBeInvalidCredentialsErrorWhenInvalidGrant() {
        values.put("error", "invalid_grant");
        values.put("error_description", "Wrong email or password.");
        APIException apiException = new APIException(values, 42);
        assertThat(apiException.isInvalidCredentials(), is(true));
    }

    @Test
    public void shouldBeInvalidCredentialsErrorWhenInvalidUserPassword() {
        values.put("error", "invalid_user_password");
        values.put("error_description", "Wrong email or password.");
        APIException apiException = new APIException(values, 42);
        assertThat(apiException.isInvalidCredentials(), is(true));
    }

    @Test
    public void shouldNotBeInvalidCredentialsErrorWhenWrongDescription() {
        values.put("error", "invalid_grant");
        values.put("error_description", "some message");
        APIException apiException = new APIException(values, 42);
        assertThat(apiException.isInvalidCredentials(), is(false));
    }

    @Test
    public void shouldBeAccessDeniedError() {
        values.put("error", "access_denied");
        APIException apiException = new APIException(values, 42);
        assertThat(apiException.isAccessDenied(), is(true));
    }

    @Test
    public void shouldBeMfaEnrollementRequiredError() {
        values.put("error", "unsupported_challenge_type");
        APIException apiException = new APIException(values, 42);
        assertThat(apiException.isMultifactorEnrollRequired(), is(true));
    }

    @Test
    public void shouldBeMfaTokenInvalidErrorForMalformedToken() {
        values.put("error", "invalid_grant");
        values.put("error_description", "Malformed mfa_token");
        APIException apiException = new APIException(values, 42);
        assertThat(apiException.isMultifactorTokenInvalid(), is(true));
    }

    @Test
    public void shouldBeMfaTokenInvalidErrorForExpiredToken() {
        values.put("error", "expired_token");
        values.put("error_description", "mfa_token is expired");
        APIException apiException = new APIException(values, 1);
        assertThat(apiException.isMultifactorTokenInvalid(), is(true));
    }

    @Test
    public void shouldNotBeMfaTokenInvalidErrorForExpiredTokenWhenWrongDescription() {
        values.put("error", "expired_token");
        values.put("error_description", "some message");
        APIException apiException = new APIException(values, 1);
        assertThat(apiException.isMultifactorTokenInvalid(), is(false));
    }

    @Test
    public void shouldNotBeMfaTokenInvalidErrorForMalformedTokenWhenWrongDescription() {
        values.put("error", "invalid_grant");
        values.put("error_description", "some message");
        APIException apiException = new APIException(values, 1);
        assertThat(apiException.isMultifactorTokenInvalid(), is(false));
    }

    @Test
    public void shouldBeMfaEnrollementRequired() {
        values.put("error", "unsupported_challenge_type");
        APIException apiException = new APIException(values, 401);
        assertThat(apiException.isMultifactorEnrollRequired(), is(true));
    }

    @Test
    public void checkErrorTypesShouldHandleNullError() {
        APIException apiException = new APIException(values, 42);
        assertThat(apiException.isAccessDenied(), is(false));
        assertThat(apiException.isInvalidCredentials(), is(false));
        assertThat(apiException.isMultifactorRequired(), is(false));
        assertThat(apiException.isMultifactorEnrollRequired(), is(false));
        assertThat(apiException.isMultifactorTokenInvalid(), is(false));
        assertThat(apiException.isVerificationRequired(), is(false));

    }
}
