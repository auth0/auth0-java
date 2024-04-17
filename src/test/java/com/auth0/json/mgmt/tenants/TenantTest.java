package com.auth0.json.mgmt.tenants;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TenantTest extends JsonTest<Tenant> {

    private static final String json = "{\"change_password\":{},\"guardian_mfa_page\":{},\"default_audience\":\"https://domain.auth0.com/myapi\",\"default_directory\":\"Username-Password-Authentication\",\"error_page\":{},\"flags\":{},\"friendly_name\":\"My-Tenant\",\"picture_url\":\"https://pic.to/123\",\"support_email\":\"support@auth0.com\",\"support_url\":\"https://support.auth0.com\",\"allowed_logout_urls\":[\"https://domain.auth0.com/logout\"], \"session_lifetime\":24, \"idle_session_lifetime\":0.5, \"session_cookie\":{\"mode\": \"persistent\"}, \"acr_values_supported\":[\"string1\",\"string2\"], \"pushed_authorization_requests_supported\": true}";


    @Test
    public void shouldSerialize() throws Exception {
        Tenant tenant = new Tenant();
        tenant.setChangePasswordPage(new PageCustomization());
        tenant.setGuardianMFAPage(new PageCustomization());
        tenant.setDefaultAudience("https://domain.auth0.com/myapi");
        tenant.setDefaultDirectory("Username-Password-Authentication");
        tenant.setErrorPage(new ErrorPageCustomization());
        tenant.setFlags(Collections.emptyMap());
        tenant.setFriendlyName("My-Tenant");
        tenant.setPictureUrl("https://pic.to/123");
        tenant.setSupportEmail("support@auth0.com");
        tenant.setSupportUrl("https://support.auth0.com");
        tenant.setAllowedLogoutUrls(Collections.singletonList("https://domain.auth0.com/logout"));
        tenant.setSessionLifetime(48);
        tenant.setIdleSessionLifetime(0);
        tenant.setSessionCookie(new SessionCookie("persistent"));
        tenant.setAcrValuesSupported(Collections.singletonList("supported acr value"));
        tenant.setPushedAuthorizationRequestsSupported(true);

        String serialized = toJSON(tenant);
        assertThat(serialized, is(notNullValue()));

        assertThat(serialized, JsonMatcher.hasEntry("change_password", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("guardian_mfa_page", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("default_audience", "https://domain.auth0.com/myapi"));
        assertThat(serialized, JsonMatcher.hasEntry("default_directory", "Username-Password-Authentication"));
        assertThat(serialized, JsonMatcher.hasEntry("error_page", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("flags", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("friendly_name", "My-Tenant"));
        assertThat(serialized, JsonMatcher.hasEntry("picture_url", "https://pic.to/123"));
        assertThat(serialized, JsonMatcher.hasEntry("support_email", "support@auth0.com"));
        assertThat(serialized, JsonMatcher.hasEntry("support_url", "https://support.auth0.com"));
        assertThat(serialized, JsonMatcher.hasEntry("allowed_logout_urls", Arrays.asList("https://domain.auth0.com/logout")));
        assertThat(serialized, JsonMatcher.hasEntry("session_lifetime", 48));
        assertThat(serialized, JsonMatcher.hasEntry("idle_session_lifetime", 0));
        assertThat(serialized, JsonMatcher.hasEntry("session_cookie", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("acr_values_supported", Collections.singletonList("supported acr value")));
        assertThat(serialized, JsonMatcher.hasEntry("pushed_authorization_requests_supported", true));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Tenant tenant = fromJSON(json, Tenant.class);

        assertThat(tenant, is(notNullValue()));
        assertThat(tenant.getChangePasswordPage(), is(notNullValue()));
        assertThat(tenant.getGuardianMFAPage(), is(notNullValue()));
        assertThat(tenant.getDefaultAudience(), is("https://domain.auth0.com/myapi"));
        assertThat(tenant.getDefaultDirectory(), is("Username-Password-Authentication"));
        assertThat(tenant.getErrorPage(), is(notNullValue()));
        assertThat(tenant.getFlags(), is(notNullValue()));
        assertThat(tenant.getFriendlyName(), is("My-Tenant"));
        assertThat(tenant.getPictureUrl(), is("https://pic.to/123"));
        assertThat(tenant.getSupportEmail(), is("support@auth0.com"));
        assertThat(tenant.getSupportUrl(), is("https://support.auth0.com"));
        assertThat(tenant.getAllowedLogoutUrls(), contains("https://domain.auth0.com/logout"));
        assertThat(tenant.getSessionLifetime(), is(24));
        assertThat(tenant.getIdleSessionLifetime(), is(0));
        assertThat(tenant.getSessionCookie(), is(notNullValue()));
        assertThat(tenant.getSessionCookie().getMode(), is("persistent"));
        assertThat(tenant.getAcrValuesSupported(), contains("string1", "string2"));
        assertThat(tenant.getPushedAuthorizationRequestsSupported(), is(true));
    }

}
