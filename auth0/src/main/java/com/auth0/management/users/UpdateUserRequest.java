/*
 * UpdateUserRequest.java
 *
 * Copyright (c) 2016 Auth0 (http://auth0.com)
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

package com.auth0.management.users;


import com.auth0.Auth0Exception;
import com.auth0.callback.BaseCallback;
import com.auth0.management.result.User;
import com.auth0.request.ParameterizableRequest;
import com.auth0.request.Request;

import java.util.Map;


/**
 * Request to update a user.
 *
 * The properties explicitly specified in the request will replace the old ones. The metadata fields are an exception
 * to this rule (`user_metadata` and `app_metadata`). These properties are merged instead of being replaced, but be
 * careful, the merge only occurs on the first level.
 */
public class UpdateUserRequest implements Request<User> {

    private static final String KEY_BLOCKED = "blocked";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_EMAIL_VERIFIED = "email_verified";
    private static final String KEY_VERIFY_EMAIL = "verify_email";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_PHONE_VERIFIED = "phone_verified";
    private static final String KEY_VERIFY_PHONE_NUMBER = "verify_phone_number";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_VERIFY_PASSWORD = "verify_password";
    private static final String KEY_USER_METADATA = "user_metadata";
    private static final String KEY_APP_METADATA = "app_metadata";
    private static final String KEY_CONNECTION = "connection";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_CLIENT_ID = "client_id";

    private final ParameterizableRequest<User> request;

    public UpdateUserRequest(ParameterizableRequest<User> request) {
        this.request = request;
    }

    @Override
    public void start(BaseCallback<User> callback) {
        request.start(callback);
    }

    @Override
    public User execute() throws Auth0Exception {
        return request.execute();
    }

    /**
     * Updates the blocked status of the user
     *
     * @param blocked the new blocked status
     * @return itself
     */
    public UpdateUserRequest setBlocked(boolean blocked) {
        request.addParameter(KEY_BLOCKED, blocked);
        return this;
    }

    /**
     * Updates the email of the user. You also need to specify the connection and the client_id properties.
     *
     * @param email the new email
     * @return itself
     */
    public UpdateUserRequest setEmail(String email) {
        request.addParameter(KEY_EMAIL, email);
        return this;
    }

    /**
     * Updates the verification status of the email of the user. You also need to specify the connection.
     *
     * @param emailVerified true if the user's email address was already verified, false otherwise
     * @return itself
     */
    public UpdateUserRequest setEmailVerified(boolean emailVerified) {
        request.addParameter(KEY_EMAIL_VERIFIED, emailVerified);
        return this;
    }

    /**
     * Indicates if the email needs to be verified. Only allowed if changing the email
     *
     * @param verifyEmail true if the email change needs to be verified, false otherwise.
     * @return itself
     */
    public UpdateUserRequest requiresEmailVerification(boolean verifyEmail) {
        request.addParameter(KEY_VERIFY_EMAIL, verifyEmail);
        return this;
    }

    /**
     * Updates the phone number of the user. You also need to specify the connection and the client_id properties.
     *
     * @param phoneNumber the new phone number
     * @return itself
     */
    public UpdateUserRequest setPhoneNumber(String phoneNumber) {
        request.addParameter(KEY_PHONE_NUMBER, phoneNumber);
        return this;
    }

    /**
     * Updates the verification status of the phone number of the user. You also need to specify the connection.
     *
     * @param phoneNumberVerified true if the user's phone number was already verified, false otherwise
     * @return itself
     */
    public UpdateUserRequest setPhoneNumberVerified(boolean phoneNumberVerified) {
        request.addParameter(KEY_PHONE_VERIFIED, phoneNumberVerified);
        return this;
    }

    /**
     * Indicates if the phone number needs to be verified. Only allowed if changing the phone number
     *
     * @param verifyPhoneNumber true if the phone number change needs to be verified, false otherwise.
     * @return itself
     */
    public UpdateUserRequest requiresPhoneNumberVerification(boolean verifyPhoneNumber) {
        request.addParameter(KEY_VERIFY_PHONE_NUMBER, verifyPhoneNumber);
        return this;
    }

    /**
     * Updates the user's password. You also need to specify the connection.
     *
     * @param password the new password
     * @return itself
     */
    public UpdateUserRequest setPassword(String password) {
        request.addParameter(KEY_PASSWORD, password);
        return this;
    }

    /**
     * Indicates if the password change needs to be verified via e-mail. Only allowed if changing the password
     *
     * @param verifyPassword true if the change needs to be verified by email, false otherwise.
     * @return itself
     */
    public UpdateUserRequest verifyPasswordByEmail(boolean verifyPassword) {
        request.addParameter(KEY_VERIFY_PASSWORD, verifyPassword);
        return this;
    }

    /**
     * Updates the user's username. You also need to specify the connection.
     *
     * @param username the new username
     * @return
     */
    public UpdateUserRequest setUsername(String username) {
        request.addParameter(KEY_USERNAME, username);
        return this;
    }

    /**
     * Updates the user's user_metadata. The metadata will be merged instead of being replaced, but be careful, the
     * merge only occurs on the first level.
     *
     * @param metadata a map with the metadata to merge with the current user_metadata.
     * @return itself
     */
    public UpdateUserRequest setUserMetadata(Map<String, Object> metadata) {
        request.addParameter(KEY_USER_METADATA, metadata);
        return this;
    }

    /**
     * Updates the user's app_metadata. The metadata will be merged instead of being replaced, but be careful, the
     * merge only occurs on the first level.
     *
     * @param metadata a map with the metadata to merge with the current app_metadata
     * @return itself
     */
    public UpdateUserRequest setAppMetadata(Map<String, Object> metadata) {
        request.addParameter(KEY_APP_METADATA, metadata);
        return this;
    }

    /**
     * Sets the connection to use for this request. You only need to explicitly set the connection in case you want to
     * update the username, password, email, phone number or the verification status of the email or phone number.
     *
     * @param connection the connection name
     * @return itself
     */
    public UpdateUserRequest forConnection(String connection) {
        request.addParameter(KEY_CONNECTION, connection);
        return this;
    }

    /**
     * Sets the client id to use for this request. You only need to explicitly set the client id in case you want to
     * update the email or phone number of the user.
     *
     * @param clientId the client id
     * @return itself
     */
    public UpdateUserRequest withClientId(String clientId) {
        request.addParameter(KEY_CLIENT_ID, clientId);
        return this;
    }
}
