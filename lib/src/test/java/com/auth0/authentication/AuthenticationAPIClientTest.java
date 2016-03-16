/*
 * AuthenticationAPIClientTest.java
 *
 * Copyright (c) 2015 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0.authentication;


import com.auth0.Auth0;
import com.auth0.Auth0Exception;
import com.auth0.authentication.result.Authentication;
import com.auth0.authentication.result.Credentials;
import com.auth0.authentication.result.DatabaseUser;
import com.auth0.authentication.result.Delegation;
import com.auth0.authentication.result.UserProfile;
import com.auth0.request.ParameterizableRequest;
import com.auth0.util.AuthenticationAPI;
import com.auth0.util.MockBaseCallback;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.auth0.util.AuthenticationAPI.GENERIC_TOKEN;
import static com.auth0.util.AuthenticationAPI.ID_TOKEN;
import static com.auth0.util.AuthenticationAPI.REFRESH_TOKEN;
import static com.auth0.util.CallbackMatcher.hasNoError;
import static com.auth0.util.CallbackMatcher.hasNoPayloadOfType;
import static com.auth0.util.CallbackMatcher.hasPayload;
import static com.auth0.util.CallbackMatcher.hasPayloadOfType;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AuthenticationAPIClientTest {

    private static final String CLIENT_ID = "CLIENTID";
    private static final String DOMAIN = "samples.auth0.com";
    private static final String CONNECTION = "DB";

    private AuthenticationAPIClient client;

    private AuthenticationAPI mockAPI;

    @Before
    public void setUp() throws Exception {
        mockAPI = new AuthenticationAPI();
        final String domain = mockAPI.getDomain();
        Auth0 auth0 = new Auth0(CLIENT_ID, domain, domain);
        client = new AuthenticationAPIClient(auth0);
    }

    @After
    public void tearDown() throws Exception {
        mockAPI.shutdown();
    }

    @Test
    public void shouldCreateClientWithAccountInfo() throws Exception {
        AuthenticationAPIClient client = new AuthenticationAPIClient(new Auth0(CLIENT_ID, DOMAIN));
        assertThat(client, is(notNullValue()));
        assertThat(client.getClientId(), equalTo(CLIENT_ID));
        assertThat(client.getBaseURL(), equalTo("https://samples.auth0.com"));
    }

    @Test
    public void shouldLoginWithResourceOwner() throws Exception {
        mockAPI.willReturnSuccessfulLogin();
        final MockBaseCallback<Credentials> callback = new MockBaseCallback<>();

        final Map<String, Object> parameters = ParameterBuilder.newAuthenticationBuilder()
                .setConnection("DB")
                .setGrantType(ParameterBuilder.GRANT_TYPE_PASSWORD)
                .set("username", "support@auth0.com")
                .set("password", "notapassword")
                .setScope(ParameterBuilder.SCOPE_OPENID)
                .asDictionary();
        client
                .loginWithResourceOwner(parameters)
                .start(callback);

        assertThat(callback, hasPayloadOfType(Credentials.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/oauth/ro"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("connection", "DB"));
        assertThat(body, hasEntry("grant_type", "password"));
        assertThat(body, hasEntry("username", "support@auth0.com"));
        assertThat(body, hasEntry("password", "notapassword"));
        assertThat(body, hasEntry("scope", "openid"));
    }

    @Test
    public void shouldLoginWithResourceOwnerSync() throws Exception {
        mockAPI.willReturnSuccessfulLogin();

        final Map<String, Object> parameters = ParameterBuilder.newAuthenticationBuilder()
                .setConnection("DB")
                .setGrantType(ParameterBuilder.GRANT_TYPE_PASSWORD)
                .set("username", "support@auth0.com")
                .set("password", "notapassword")
                .setScope(ParameterBuilder.SCOPE_OPENID)
                .asDictionary();

        final Credentials credentials = client
                .loginWithResourceOwner(parameters)
                .execute();

        assertThat(credentials, is(notNullValue()));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/oauth/ro"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("connection", "DB"));
        assertThat(body, hasEntry("grant_type", "password"));
        assertThat(body, hasEntry("username", "support@auth0.com"));
        assertThat(body, hasEntry("password", "notapassword"));
        assertThat(body, hasEntry("scope", "openid"));
    }

    @Test
    public void shouldFailLoginWithResourceOwner() throws Exception {
        mockAPI.willReturnFailedLogin();
        final MockBaseCallback<Credentials> callback = new MockBaseCallback<>();

        final Map<String, Object> parameters = ParameterBuilder.newAuthenticationBuilder()
                .setConnection(CONNECTION)
                .setGrantType(ParameterBuilder.GRANT_TYPE_PASSWORD)
                .set("username", "support@auth0.com")
                .set("password", "notapassword")
                .asDictionary();
        client
                .loginWithResourceOwner(parameters)
                .start(callback);

        assertThat(callback, hasNoPayloadOfType(Credentials.class));
    }

    @Test
    public void shouldFailLoginWithResourceOwnerSync() throws Exception {
        mockAPI.willReturnFailedLogin();
        final MockBaseCallback<Credentials> callback = new MockBaseCallback<>();

        final Map<String, Object> parameters = ParameterBuilder.newAuthenticationBuilder()
                .setConnection(CONNECTION)
                .setGrantType(ParameterBuilder.GRANT_TYPE_PASSWORD)
                .set("username", "support@auth0.com")
                .set("password", "notapassword")
                .asDictionary();

        Exception exception = null;
        try {
            client.loginWithResourceOwner(parameters).execute();
        } catch (Auth0Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
    }

    @Test
    public void shouldLoginWithUserAndPassword() throws Exception {
        mockAPI
                .willReturnSuccessfulLogin();

        final MockBaseCallback<Credentials> callback = new MockBaseCallback<>();

        client
                .login("support@auth0.com", "voidpassword")
                .start(callback);

        assertThat(callback, hasPayloadOfType(Credentials.class));
    }

    @Test
    public void shouldLoginWithUserAndPasswordSync() throws Exception {
        mockAPI
                .willReturnSuccessfulLogin();

        final Credentials credentials = client
                .login("support@auth0.com", "voidpassword")
                .execute();

        assertThat(credentials, is(notNullValue()));
    }

    @Test
    public void shouldFetchTokenInfo() throws Exception {
        mockAPI.willReturnTokenInfo();
        final MockBaseCallback<UserProfile> callback = new MockBaseCallback<>();

        client.tokenInfo("ID_TOKEN")
                .start(callback);

        assertThat(callback, hasPayloadOfType(UserProfile.class));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/tokeninfo"));
    }

    @Test
    public void shouldFetchTokenInfoSync() throws Exception {
        mockAPI.willReturnTokenInfo();

        final UserProfile profile = client
                .tokenInfo("ID_TOKEN")
                .execute();

        assertThat(profile, is(notNullValue()));

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/tokeninfo"));
    }

    @Test
    public void shouldLoginWithOAuthAccessToken() throws Exception {
        mockAPI
                .willReturnSuccessfulLogin();

        final MockBaseCallback<Credentials> callback = new MockBaseCallback<>();
        client.loginWithOAuthAccessToken("fbtoken", "facebook")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/oauth/access_token"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("connection", "facebook"));
        assertThat(body, hasEntry("access_token", "fbtoken"));
        assertThat(body, hasEntry("scope", "openid offline_access"));

        assertThat(callback, hasPayloadOfType(Credentials.class));
    }

    @Test
    public void shouldLoginWithOAuthAccessTokenSync() throws Exception {
        mockAPI.willReturnSuccessfulLogin();

        final Credentials credentials = client
                .loginWithOAuthAccessToken("fbtoken", "facebook")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/oauth/access_token"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("connection", "facebook"));
        assertThat(body, hasEntry("access_token", "fbtoken"));
        assertThat(body, hasEntry("scope", "openid offline_access"));

        assertThat(credentials, is(notNullValue()));
    }

    @Test
    public void shouldLoginWithPhoneNumber() throws Exception {
        mockAPI.willReturnSuccessfulLogin();

        final MockBaseCallback<Credentials> callback = new MockBaseCallback<>();
        client.loginWithPhoneNumber("+10101010101", "1234")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/oauth/ro"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("connection", "sms"));
        assertThat(body, hasEntry("username", "+10101010101"));
        assertThat(body, hasEntry("password", "1234"));
        assertThat(body, hasEntry("scope", "openid offline_access"));

        assertThat(callback, hasPayloadOfType(Credentials.class));
    }

    @Test
    public void shouldLoginWithPhoneNumberSync() throws Exception {
        mockAPI.willReturnSuccessfulLogin();

        final Credentials credentials = client
                .loginWithPhoneNumber("+10101010101", "1234")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/oauth/ro"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("connection", "sms"));
        assertThat(body, hasEntry("username", "+10101010101"));
        assertThat(body, hasEntry("password", "1234"));
        assertThat(body, hasEntry("scope", "openid offline_access"));

        assertThat(credentials, is(notNullValue()));
    }

    @Test
    public void shouldLoginWithEmailOnly() throws Exception {
        mockAPI.willReturnSuccessfulLogin();

        final MockBaseCallback<Credentials> callback = new MockBaseCallback<>();
        client.loginWithEmail("support@auth0.com", "1234")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/oauth/ro"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("connection", "email"));
        assertThat(body, hasEntry("username", "support@auth0.com"));
        assertThat(body, hasEntry("password", "1234"));
        assertThat(body, hasEntry("scope", "openid offline_access"));

        assertThat(callback, hasPayloadOfType(Credentials.class));
    }

    @Test
    public void shouldLoginWithEmailOnlySync() throws Exception {
        mockAPI
                .willReturnSuccessfulLogin()
                .willReturnTokenInfo();

        final Credentials credentials = client
                .loginWithEmail("support@auth0.com", "1234")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/oauth/ro"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("connection", "email"));
        assertThat(body, hasEntry("username", "support@auth0.com"));
        assertThat(body, hasEntry("password", "1234"));
        assertThat(body, hasEntry("scope", "openid offline_access"));

        assertThat(credentials, is(notNullValue()));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        mockAPI.willReturnSuccessfulSignUp();

        final MockBaseCallback<DatabaseUser> callback = new MockBaseCallback<>();
        client.createUser("support@auth0.com", "123123123", "support")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/signup"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("username", "support"));
        assertThat(body, hasEntry("password", "123123123"));

        assertThat(callback, hasPayloadOfType(DatabaseUser.class));
    }

    @Test
    public void shouldCreateUserSync() throws Exception {
        mockAPI.willReturnSuccessfulSignUp();

        final DatabaseUser user = client
                .createUser("support@auth0.com", "123123123", "support")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/signup"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("username", "support"));
        assertThat(body, hasEntry("password", "123123123"));

        assertThat(user, is(notNullValue()));
    }

    @Test
    public void shouldCreateUserWithoutUsername() throws Exception {
        mockAPI.willReturnSuccessfulSignUp();

        final MockBaseCallback<DatabaseUser> callback = new MockBaseCallback<>();
        client.createUser("support@auth0.com", "123123123")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/signup"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, not(hasEntry("username", "support")));
        assertThat(body, hasEntry("password", "123123123"));

        assertThat(callback, hasPayloadOfType(DatabaseUser.class));
    }

    @Test
    public void shouldCreateUserWithoutUsernameSync() throws Exception {
        mockAPI.willReturnSuccessfulSignUp();

        final DatabaseUser user = client
                .createUser("support@auth0.com", "123123123")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/signup"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("username", null));
        assertThat(body, hasEntry("password", "123123123"));

        assertThat(user, is(notNullValue()));
    }

    @Test
    public void shouldSignUpUser() throws Exception {
        mockAPI.willReturnSuccessfulSignUp()
                .willReturnSuccessfulLogin()
                .willReturnTokenInfo();

        final MockBaseCallback<Credentials> callback = new MockBaseCallback<>();
        client.signUp("support@auth0.com", "123123123", "support")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/signup"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("username", "support"));
        assertThat(body, hasEntry("password", "123123123"));

        assertThat(callback, hasPayloadOfType(Credentials.class));
    }

    @Test
    public void shouldSignUpUserSync() throws Exception {
        mockAPI.willReturnSuccessfulSignUp()
                .willReturnSuccessfulLogin()
                .willReturnTokenInfo();

        final Credentials credentials = client
                .signUp("support@auth0.com", "123123123", "support")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/signup"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("username", "support"));
        assertThat(body, hasEntry("password", "123123123"));

        assertThat(credentials, is(notNullValue()));
    }

    @Test
    public void shouldSignUpUserWithoutUsername() throws Exception {
        mockAPI.willReturnSuccessfulSignUp()
                .willReturnSuccessfulLogin()
                .willReturnTokenInfo();

        final MockBaseCallback<Credentials> callback = new MockBaseCallback<>();
        client.signUp("support@auth0.com", "123123123")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/signup"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, not(hasEntry("username", "support")));
        assertThat(body, hasEntry("password", "123123123"));

        assertThat(callback, hasPayloadOfType(Credentials.class));
    }

    @Test
    public void shouldSignUpUserWithoutUsernameSync() throws Exception {
        mockAPI.willReturnSuccessfulSignUp()
                .willReturnSuccessfulLogin()
                .willReturnTokenInfo();

        final Credentials credentials = client
                .signUp("support@auth0.com", "123123123")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/signup"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("username", null));
        assertThat(body, hasEntry("password", "123123123"));

        assertThat(credentials, is(notNullValue()));
    }

    @Test
    public void shouldChangePassword() throws Exception {
        mockAPI.willReturnSuccessfulChangePassword();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.requestChangePassword("support@auth0.com")
                .setNewPassword("123123123")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/change_password"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, not(hasKey("username")));
        assertThat(body, hasEntry("password", "123123123"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldChangePasswordSync() throws Exception {
        mockAPI.willReturnSuccessfulChangePassword();

        client.requestChangePassword("support@auth0.com")
                .setNewPassword("123123123")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/change_password"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, not(hasKey("username")));
        assertThat(body, hasEntry("password", "123123123"));
    }

    @Test
    public void shouldRequestChangePassword() throws Exception {
        mockAPI.willReturnSuccessfulChangePassword();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.requestChangePassword("support@auth0.com")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/change_password"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, not(hasKey("username")));
        assertThat(body, not(hasKey("password")));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldRequestChangePasswordSync() throws Exception {
        mockAPI.willReturnSuccessfulChangePassword();

        client.requestChangePassword("support@auth0.com")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/dbconnections/change_password"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, not(hasKey("username")));
        assertThat(body, not(hasKey("password")));
    }

    @Test
    public void shouldCallDelegation() throws Exception {
        mockAPI.willReturnGenericDelegationToken();

        final MockBaseCallback<Map<String, Object>> callback = new MockBaseCallback<>();
        client.delegation()
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/delegation"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("grant_type", ParameterBuilder.GRANT_TYPE_JWT));
        assertThat(body, hasEntry("client_id", CLIENT_ID));

        Map<String, Object> payload = new HashMap<>();
        payload.put("token", GENERIC_TOKEN);
        assertThat(callback, hasPayload(payload));
    }

    @Test
    public void shouldCallDelegationSync() throws Exception {
        mockAPI.willReturnGenericDelegationToken();

        final Map<String, Object> response = client
                .delegation()
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/delegation"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("grant_type", ParameterBuilder.GRANT_TYPE_JWT));
        assertThat(body, hasEntry("client_id", CLIENT_ID));

        Map<String, Object> payload = new HashMap<>();
        payload.put("token", GENERIC_TOKEN);
        assertThat(response, is(equalTo(payload)));
    }

    @Test
    public void shouldGetNewIdTokenWithIdToken() throws Exception {
        mockAPI.willReturnNewIdToken();

        final MockBaseCallback<Delegation> callback = new MockBaseCallback<>();
        client.delegationWithIdToken(ID_TOKEN)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/delegation"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("grant_type", ParameterBuilder.GRANT_TYPE_JWT));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("api_type", "app"));
        assertThat(body, hasEntry("id_token", ID_TOKEN));

        assertThat(callback, hasPayloadOfType(Delegation.class));
    }

    @Test
    public void shouldGetNewIdTokenWithIdTokenSync() throws Exception {
        mockAPI.willReturnNewIdToken();

        final Delegation delegation = client
                .delegationWithIdToken(ID_TOKEN)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/delegation"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("grant_type", ParameterBuilder.GRANT_TYPE_JWT));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("api_type", "app"));
        assertThat(body, hasEntry("id_token", ID_TOKEN));

        assertThat(delegation, is(notNullValue()));
    }

    @Test
    public void shouldGetCustomizedDelegationRequestWithIdToken() throws Exception {
        mockAPI.willReturnNewIdToken();

        final MockBaseCallback<Map<String, Object>> callback = new MockBaseCallback<>();
        client.delegationWithIdToken(ID_TOKEN, "custom_api_type")
                .setScope("custom_scope")
                .setTarget("custom_target")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/delegation"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("grant_type", ParameterBuilder.GRANT_TYPE_JWT));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("api_type", "custom_api_type"));
        assertThat(body, hasEntry("scope", "custom_scope"));
        assertThat(body, hasEntry("target", "custom_target"));
        assertThat(body, hasEntry("id_token", ID_TOKEN));
    }

    @Test
    public void shouldGetCustomizedDelegationRequestWithIdTokenSync() throws Exception {
        mockAPI.willReturnNewIdToken();

        client
                .delegationWithIdToken(ID_TOKEN, "custom_api_type")
                .setScope("custom_scope")
                .setTarget("custom_target")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/delegation"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("grant_type", ParameterBuilder.GRANT_TYPE_JWT));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("api_type", "custom_api_type"));
        assertThat(body, hasEntry("scope", "custom_scope"));
        assertThat(body, hasEntry("target", "custom_target"));
        assertThat(body, hasEntry("id_token", ID_TOKEN));
    }

    @Test
    public void shouldGetNewIdTokenWithRefreshToken() throws Exception {
        mockAPI.willReturnNewIdToken();

        final MockBaseCallback<Delegation> callback = new MockBaseCallback<>();
        client.delegationWithRefreshToken(REFRESH_TOKEN)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/delegation"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("grant_type", ParameterBuilder.GRANT_TYPE_JWT));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("api_type", "app"));
        assertThat(body, hasEntry("refresh_token", REFRESH_TOKEN));

        assertThat(callback, hasPayloadOfType(Delegation.class));
    }

    @Test
    public void shouldGetNewIdTokenWithRefreshTokenSync() throws Exception {
        mockAPI.willReturnNewIdToken();

        final Delegation delegation = client
                .delegationWithRefreshToken(REFRESH_TOKEN)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/delegation"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("grant_type", ParameterBuilder.GRANT_TYPE_JWT));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("api_type", "app"));
        assertThat(body, hasEntry("refresh_token", REFRESH_TOKEN));

        assertThat(delegation, is(notNullValue()));
    }

    @Test
    public void shouldGetCustomizedDelegationRequestWithRefreshToken() throws Exception {
        mockAPI.willReturnNewIdToken();

        final MockBaseCallback<Map<String, Object>> callback = new MockBaseCallback<>();
        client.delegationWithRefreshToken(REFRESH_TOKEN, "custom_api_type")
                .setScope("custom_scope")
                .setTarget("custom_target")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/delegation"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("grant_type", ParameterBuilder.GRANT_TYPE_JWT));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("api_type", "custom_api_type"));
        assertThat(body, hasEntry("scope", "custom_scope"));
        assertThat(body, hasEntry("target", "custom_target"));
        assertThat(body, hasEntry("refresh_token", REFRESH_TOKEN));
    }

    @Test
    public void shouldGetCustomizedDelegationRequestWithRefreshTokenSync() throws Exception {
        mockAPI.willReturnNewIdToken();

        client
                .delegationWithRefreshToken(REFRESH_TOKEN, "custom_api_type")
                .setScope("custom_scope")
                .setTarget("custom_target")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/delegation"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("grant_type", ParameterBuilder.GRANT_TYPE_JWT));
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("api_type", "custom_api_type"));
        assertThat(body, hasEntry("scope", "custom_scope"));
        assertThat(body, hasEntry("target", "custom_target"));
        assertThat(body, hasEntry("refresh_token", REFRESH_TOKEN));
    }

    @Test
    public void shouldUnlinkAccount() throws Exception {
        mockAPI.willReturnSuccessfulUnlinkAccount();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.unlink("user id", "access token")
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/unlink"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("clientID", CLIENT_ID));
        assertThat(body, hasEntry("user_id", "user id"));
        assertThat(body, hasEntry("access_token", "access token"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldUnlinkAccountSync() throws Exception {
        mockAPI.willReturnSuccessfulUnlinkAccount();

        client.unlink("user id", "access token")
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/unlink"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("clientID", CLIENT_ID));
        assertThat(body, hasEntry("user_id", "user id"));
        assertThat(body, hasEntry("access_token", "access token"));
    }

    @Test
    public void shouldStartPasswordless() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        final Map<String, Object> parameters = ParameterBuilder.newBuilder()
                .setConnection("email")
                .set("send", "code")
                .set("email", "support@auth0.com")
                .asDictionary();

        client.passwordless()
                .addParameters(parameters)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("send", "code"));
        assertThat(body, hasEntry("connection", "email"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldStartPasswordlessSync() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        final Map<String, Object> parameters = ParameterBuilder.newBuilder()
                .setConnection("email")
                .set("send", "code")
                .set("email", "support@auth0.com")
                .asDictionary();

        client.passwordless()
                .addParameters(parameters)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("send", "code"));
        assertThat(body, hasEntry("connection", "email"));
    }

    @Test
    public void shouldSendEmailCode() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.passwordlessWithEmail("support@auth0.com", PasswordlessType.CODE)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("send", "code"));
        assertThat(body, hasEntry("connection", "email"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldSendEmailCodeSync() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        client.passwordlessWithEmail("support@auth0.com", PasswordlessType.CODE)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("send", "code"));
        assertThat(body, hasEntry("connection", "email"));
    }

    @Test
    public void shouldSendEmailLink() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.passwordlessWithEmail("support@auth0.com", PasswordlessType.LINK)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("send", "link"));
        assertThat(body, hasEntry("connection", "email"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldSendEmailLinkSync() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        client.passwordlessWithEmail("support@auth0.com", PasswordlessType.LINK)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("send", "link"));
        assertThat(body, hasEntry("connection", "email"));
    }

    @Test
    public void shouldSendEmailLinkAndroid() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.passwordlessWithEmail("support@auth0.com", PasswordlessType.LINK_ANDROID)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("send", "link_android"));
        assertThat(body, hasEntry("connection", "email"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldSendEmailLinkAndroidSync() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        client.passwordlessWithEmail("support@auth0.com", PasswordlessType.LINK_ANDROID)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("send", "link_android"));
        assertThat(body, hasEntry("connection", "email"));
    }

    @Test
    public void shouldSendEmailLinkIOS() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.passwordlessWithEmail("support@auth0.com", PasswordlessType.LINK_IOS)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("send", "link_ios"));
        assertThat(body, hasEntry("connection", "email"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldSendEmailLinkIOSSync() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        client.passwordlessWithEmail("support@auth0.com", PasswordlessType.LINK_IOS)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("email", "support@auth0.com"));
        assertThat(body, hasEntry("send", "link_ios"));
        assertThat(body, hasEntry("connection", "email"));
    }

    @Test
    public void shouldSendSMSCode() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.passwordlessWithSMS("+1123123123", PasswordlessType.CODE)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("phone_number", "+1123123123"));
        assertThat(body, hasEntry("send", "code"));
        assertThat(body, hasEntry("connection", "sms"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldSendSMSCodeSync() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        client.passwordlessWithSMS("+1123123123", PasswordlessType.CODE)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("phone_number", "+1123123123"));
        assertThat(body, hasEntry("send", "code"));
        assertThat(body, hasEntry("connection", "sms"));
    }

    @Test
    public void shouldSendSMSLink() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.passwordlessWithSMS("+1123123123", PasswordlessType.LINK)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("phone_number", "+1123123123"));
        assertThat(body, hasEntry("send", "link"));
        assertThat(body, hasEntry("connection", "sms"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldSendSMSLinkSync() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        client.passwordlessWithSMS("+1123123123", PasswordlessType.LINK)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("phone_number", "+1123123123"));
        assertThat(body, hasEntry("send", "link"));
        assertThat(body, hasEntry("connection", "sms"));
    }

    @Test
    public void shouldSendSMSLinkAndroid() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.passwordlessWithSMS("+1123123123", PasswordlessType.LINK_ANDROID)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("phone_number", "+1123123123"));
        assertThat(body, hasEntry("send", "link_android"));
        assertThat(body, hasEntry("connection", "sms"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldSendSMSLinkAndroidSync() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        client.passwordlessWithSMS("+1123123123", PasswordlessType.LINK_ANDROID)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("phone_number", "+1123123123"));
        assertThat(body, hasEntry("send", "link_android"));
        assertThat(body, hasEntry("connection", "sms"));
    }

    @Test
    public void shouldSendSMSLinkIOS() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        final MockBaseCallback<Void> callback = new MockBaseCallback<>();
        client.passwordlessWithSMS("+1123123123", PasswordlessType.LINK_IOS)
                .start(callback);

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("phone_number", "+1123123123"));
        assertThat(body, hasEntry("send", "link_ios"));
        assertThat(body, hasEntry("connection", "sms"));

        assertThat(callback, hasNoError());
    }

    @Test
    public void shouldSendSMSLinkIOSSync() throws Exception {
        mockAPI.willReturnSuccessfulPasswordlessStart();

        client.passwordlessWithSMS("+1123123123", PasswordlessType.LINK_IOS)
                .execute();

        final RecordedRequest request = mockAPI.takeRequest();
        assertThat(request.getPath(), equalTo("/passwordless/start"));

        Map<String, String> body = bodyFromRequest(request);
        assertThat(body, hasEntry("client_id", CLIENT_ID));
        assertThat(body, hasEntry("phone_number", "+1123123123"));
        assertThat(body, hasEntry("send", "link_ios"));
        assertThat(body, hasEntry("connection", "sms"));
    }

    @Test
    public void shouldFetchProfileAfterLoginRequest() throws Exception {
        mockAPI
                .willReturnSuccessfulLogin()
                .willReturnTokenInfo();

        MockBaseCallback<Authentication> callback = new MockBaseCallback<>();
        client.getProfileAfter(client.login("support@auth0.com", "voidpassword"))
                .start(callback);

        final RecordedRequest firstRequest = mockAPI.takeRequest();
        assertThat(firstRequest.getPath(), equalTo("/oauth/ro"));

        Map<String, String> body = bodyFromRequest(firstRequest);
        assertThat(body, hasEntry("username", "support@auth0.com"));
        assertThat(body, hasEntry("password", "voidpassword"));

        final RecordedRequest secondRequest = mockAPI.takeRequest();
        assertThat(secondRequest.getPath(), equalTo("/tokeninfo"));

        assertThat(callback, hasPayloadOfType(Authentication.class));

    }

    private Map<String, String> bodyFromRequest(RecordedRequest request) throws java.io.IOException {
        return new ObjectMapper().readValue(request.getBody().inputStream(), new TypeReference<Map<String, String>>() {
        });
    }
}