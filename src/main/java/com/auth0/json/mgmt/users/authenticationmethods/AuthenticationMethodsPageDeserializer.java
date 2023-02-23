package com.auth0.json.mgmt.users.authenticationmethods;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a paged response into its {@linkplain AuthenticationMethodsPage} representation.
 */
public class AuthenticationMethodsPageDeserializer extends PageDeserializer<AuthenticationMethodsPage, AuthenticationMethod> {

    protected AuthenticationMethodsPageDeserializer() {
        super(AuthenticationMethod.class, "authenticators");
    }

    @Override
    protected AuthenticationMethodsPage createPage(List<AuthenticationMethod> items) {
        return new AuthenticationMethodsPage(items);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected AuthenticationMethodsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<AuthenticationMethod> items) {
        return new AuthenticationMethodsPage(start, length, total, limit, items);
    }

    @Override
    protected AuthenticationMethodsPage createPage(Integer start, Integer length, Integer total, Integer limit, String next, List<AuthenticationMethod> items) {
        return new AuthenticationMethodsPage(start, length, total, limit, next, items);
    }
}

