package com.auth0.client.mgmt;

import com.auth0.json.mgmt.branding.*;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.MGMT_BRANDING_LOGIN_TEMPLATE;
import static com.auth0.client.MockServer.MGMT_BRANDING_SETTINGS;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BrandingEntityTest extends BaseMgmtEntityTest {
    @Test
    public void shouldGetBrandingSettings() throws Exception {
        Request<BrandingSettings> request = api.branding().getBrandingSettings();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_BRANDING_SETTINGS, 200);
        BrandingSettings response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/branding"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateBrandingSettingsWithNullSettings() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.branding().updateBrandingSettings(null),
            "'settings' cannot be null!");
    }

    @Test
    public void shouldUpdateBrandingSettings() throws Exception {
        Request<BrandingSettings> request = api.branding().updateBrandingSettings(getBrandingSettings());
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_BRANDING_SETTINGS, 200);
        BrandingSettings response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/branding"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetTheUniversalLoginTemplate() throws Exception {
        Request<UniversalLoginTemplate> request = api.branding().getUniversalLoginTemplate();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_BRANDING_LOGIN_TEMPLATE, 200);
        UniversalLoginTemplate response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/branding/templates/universal-login"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldDeleteTheUniversalLoginTemplate() throws Exception {
        Request<Void> request = api.branding().deleteUniversalLoginTemplate();
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/branding/templates/universal-login"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnSetUniversalLoginTemplateNullTemplate() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.branding().setUniversalLoginTemplate(null),
            "'template' cannot be null!");
    }

    @Test
    public void shouldSetTheUniversalLoginTemplate() throws Exception {
        Request<Void> request = api.branding().setUniversalLoginTemplate(getUniversalLoginTemplateUpdate());
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/branding/templates/universal-login"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    private BrandingSettings getBrandingSettings() {
        BrandingSettings settings = new BrandingSettings();

        BrandingColors colors = new BrandingColors();
        colors.setPrimary("#00000");
        colors.setPageBackground("#ffffff");
        settings.setColors(colors);

        settings.setFaviconUrl("https://test-url.com/favicon.svg");
        settings.setLogoUrl("https://test-url.com/logo.svg");

        BrandingFont font = new BrandingFont();
        font.setUrl("https://test.com/font.ttf");
        settings.setFont(font);

        return settings;

    }

    private UniversalLoginTemplateUpdate getUniversalLoginTemplateUpdate(){
        UniversalLoginTemplateUpdate template = new UniversalLoginTemplateUpdate();
        template.setTemplate("<!DOCTYPE html><html><head>{%- auth0:head -%}</head><body>{%- auth0:widget -%}</body></html>");
        return template;
    }
}
