package com.auth0.json.mgmt.users.authenticationmethods;

import com.auth0.json.mgmt.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents a page of an AuthenticationMethod.
 * @see AuthenticationMethod
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = AuthenticationMethodsPageDeserializer.class)
public class AuthenticationMethodsPage extends Page<AuthenticationMethod> {
    public AuthenticationMethodsPage(List<AuthenticationMethod> items) {
        super(items);
    }

    /**
     * @deprecated use {@linkplain AuthenticationMethodsPage#AuthenticationMethodsPage(Integer, Integer, Integer, Integer, String, List)} instead.
     */
    @Deprecated
    public AuthenticationMethodsPage(Integer start, Integer length, Integer total, Integer limit, List<AuthenticationMethod> items) {
        super(start, length, total, limit, items);
    }

    public AuthenticationMethodsPage(Integer start, Integer length, Integer total, Integer limit, String next, List<AuthenticationMethod> items) {
        super(start, length, total, limit, next, items);
    }
}

