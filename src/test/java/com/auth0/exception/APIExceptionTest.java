package com.auth0.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class APIExceptionTest {

    private Map<String, Object> values;

    private final static int ERROR_CODE = 42;
    private final static String EXPECTED_ERROR_MESSAGE_PREFIX = "Request failed with status code " + ERROR_CODE + ": ";

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
    }

    @Test
    public void shouldGetMessageFromErrorDescription() {
        values.put("error_description", "some error description");
        values.put("description", "some description");
        values.put("message", "some message");
        values.put("error", "some error");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.getMessage(), is(EXPECTED_ERROR_MESSAGE_PREFIX + "some error description"));
    }

    @Test
    public void shouldGetMessageFromDescriptionAsString() {
        values.put("message", "some message");
        values.put("error", "some error");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.getMessage(), is(EXPECTED_ERROR_MESSAGE_PREFIX + "some message"));
    }

    @Test
    public void shouldGetMessageFromDescriptionAsMap() {
        Map<String, Object> rules = new HashMap<>();
        rules.put("verified", false);
        rules.put("code", "lengthAtLeast");
        rules.put("format", Collections.singletonList(8));
        rules.put("message", "some password length message");

        Map<String, Object> passwordStrengthError = new HashMap<>();
        passwordStrengthError.put("rules", Collections.singletonList(rules));

        values.put("description", passwordStrengthError);
        values.put("message", "some message");
        values.put("error", "some error");

        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.getMessage(), is(EXPECTED_ERROR_MESSAGE_PREFIX + "some password length message"));
    }

    @Test
    public void shouldGetDefaultMessageWhenDescriptionNotMapOrString() {
        values.put("description", Collections.singletonList("some description"));

        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.getMessage(), is(EXPECTED_ERROR_MESSAGE_PREFIX + "Unknown exception"));
    }

    @Test
    public void shouldGetMessageFromMessage() {
        values.put("message", "some message");
        values.put("error", "some error");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.getMessage(), is(EXPECTED_ERROR_MESSAGE_PREFIX + "some message"));
    }

    @Test
    public void shouldGetMessageFromError() {
        values.put("error", "some error");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.getMessage(), is(EXPECTED_ERROR_MESSAGE_PREFIX + "some error"));
    }

    @Test
    public void shouldGetDefaultMessage() {
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.getMessage(), is(EXPECTED_ERROR_MESSAGE_PREFIX + "Unknown exception"));
    }

    @Test
    public void shouldBeMfaRequiredWithMfaTokenError() {
        values.put("error", "mfa_required");
        values.put("mfa_token", "some-mfa-token");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isMultifactorRequired(), is(true));
        assertThat(apiException.getValue("mfa_token"), is("some-mfa-token"));
    }

    @Test
    public void shouldBeInvalidCredentialsErrorWhenInvalidGrant() {
        values.put("error", "invalid_grant");
        values.put("error_description", "Wrong email or password.");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isInvalidCredentials(), is(true));
    }

    @Test
    public void shouldBeInvalidCredentialsErrorWhenInvalidUserPassword() {
        values.put("error", "invalid_user_password");
        values.put("error_description", "Wrong email or password.");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isInvalidCredentials(), is(true));
    }

    @Test
    public void shouldNotBeInvalidCredentialsErrorWhenWrongDescription() {
        values.put("error", "invalid_grant");
        values.put("error_description", "some message");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isInvalidCredentials(), is(false));
    }

    @Test
    public void shouldBeAccessDeniedError() {
        values.put("error", "access_denied");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isAccessDenied(), is(true));
    }

    @Test
    public void shouldBeMfaEnrollementRequiredError() {
        values.put("error", "unsupported_challenge_type");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isMultifactorEnrollRequired(), is(true));
    }

    @Test
    public void shouldBeMfaTokenInvalidErrorForMalformedToken() {
        values.put("error", "invalid_grant");
        values.put("error_description", "Malformed mfa_token");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isMultifactorTokenInvalid(), is(true));
    }

    @Test
    public void shouldBeMfaTokenInvalidErrorForExpiredToken() {
        values.put("error", "expired_token");
        values.put("error_description", "mfa_token is expired");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isMultifactorTokenInvalid(), is(true));
    }

    @Test
    public void shouldNotBeMfaTokenInvalidErrorForExpiredTokenWhenWrongDescription() {
        values.put("error", "expired_token");
        values.put("error_description", "some message");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isMultifactorTokenInvalid(), is(false));
    }

    @Test
    public void shouldNotBeMfaTokenInvalidErrorForMalformedTokenWhenWrongDescription() {
        values.put("error", "invalid_grant");
        values.put("error_description", "some message");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isMultifactorTokenInvalid(), is(false));
    }

    @Test
    public void shouldBeMfaEnrollementRequired() {
        values.put("error", "unsupported_challenge_type");
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isMultifactorEnrollRequired(), is(true));
    }

    @Test
    public void checkErrorTypesShouldHandleNullError() {
        APIException apiException = new APIException(values, ERROR_CODE);
        assertThat(apiException.isAccessDenied(), is(false));
        assertThat(apiException.isInvalidCredentials(), is(false));
        assertThat(apiException.isMultifactorRequired(), is(false));
        assertThat(apiException.isMultifactorEnrollRequired(), is(false));
        assertThat(apiException.isMultifactorTokenInvalid(), is(false));
        assertThat(apiException.isVerificationRequired(), is(false));
    }

    // error description non-string and null

    @Test
    public void shouldHandleNonStringMessageForMessageError() {
        Map<String, Object> vals = Collections.singletonMap("error", 42);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getDescription(), is("42"));
    }

    @Test
    public void shouldHandleNonStringMessageForMessageErrorDescription() {
        Map<String, Object> vals = Collections.singletonMap("error_description", 42);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getDescription(), is("42"));
    }

    @Test
    public void shouldHandleNonStringMessageForErrorMessageMessage() {
        Map<String, Object> vals = Collections.singletonMap("message", 42);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getDescription(), is("42"));
    }

    @Test
    public void shouldHandleNullMessageForMessageErrorDescription() {
        Map<String, Object> vals = Collections.singletonMap("error_description", null);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getDescription(), is(nullValue()));
    }

    @Test
    public void shouldHandleNullMessageForMessageVal() {
        Map<String, Object> vals = Collections.singletonMap("message", null);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getDescription(), is(nullValue()));
    }

    @Test
    public void shouldHandleNullMessageForErrorVal() {
        Map<String, Object> vals = Collections.singletonMap("error", null);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getDescription(), is(nullValue()));
    }

    // error code non-string and null

    @Test
    public void shouldHandleNonStringMessageForError() {
        Map<String, Object> vals = Collections.singletonMap("errorCode", 42);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getError(), is("42"));
    }

    @Test
    public void shouldHandleNonStringMessageForErrorDescription() {
        Map<String, Object> vals = Collections.singletonMap("error", 42);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getError(), is("42"));
    }

    @Test
    public void shouldHandleNonStringMessageForMessage() {
        Map<String, Object> vals = Collections.singletonMap("code", 42);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getError(), is("42"));
    }

    @Test
    public void shouldHandleNullMessageForMessage() {
        Map<String, Object> vals = Collections.singletonMap("error", null);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getError(), is(nullValue()));
    }

    @Test
    public void shouldHandleNullMessageForError() {
        Map<String, Object> vals = Collections.singletonMap("code", null);
        APIException apiException = new APIException(vals, ERROR_CODE);
        assertThat(apiException.getError(), is(nullValue()));
    }
}
