package com.auth0.client.mgmt;

import com.auth0.json.mgmt.attackprotection.*;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AttackProtectionEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListBreachedPasswordSettings() throws Exception {
        Request<BreachedPassword> request = api.attackProtection().getBreachedPasswordSettings();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(BREACHED_PASSWORD_SETTINGS, 200);
        BreachedPassword response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/attack-protection/breached-password-detection"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getEnabled(), is(true));
        assertThat(response.getMethod(), is("standard"));
        assertThat(response.getShields(), is(notNullValue()));
        assertThat(response.getShields().size(), is(2));
        assertThat(response.getShields(), contains("block", "admin_notification"));
        assertThat(response.getAdminNotificationFrequency(), is(notNullValue()));
        assertThat(response.getAdminNotificationFrequency().size(), is(2));
        assertThat(response.getAdminNotificationFrequency(), contains("immediately", "weekly"));
        assertThat(response.getStage().getPreUserRegistrationStage().getShields(), contains("admin_notification"));
    }

    @Test
    public void shouldUpdateBreachedPasswordSettings() throws Exception {
        BreachedPasswordStage stage = new BreachedPasswordStage();
        BreachedPasswordStageEntry preReg = new BreachedPasswordStageEntry();
        preReg.setShields(Arrays.asList("admin_notification"));
        stage.setPreUserRegistrationStage(preReg);

        BreachedPassword breachedPassword = new BreachedPassword();
        breachedPassword.setEnabled(true);
        breachedPassword.setMethod("standard");
        breachedPassword.setAdminNotificationFrequency(Arrays.asList("immediately", "daily"));
        breachedPassword.setShields(Arrays.asList("admin_notification", "block"));
        breachedPassword.setStage(stage);

        Request<BreachedPassword> request = api.attackProtection().updateBreachedPasswordSettings(breachedPassword);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(BREACHED_PASSWORD_SETTINGS, 200);
        BreachedPassword response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/attack-protection/breached-password-detection"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        System.out.println(body);

        assertThat(body.size(), is(5));
        assertThat(body.get("method"), is("standard"));
        assertThat(body.get("enabled"), is(true));
        assertThat(((List<?>)body.get("shields")).size(), is(2));
        assertThat((List<?>) body.get("shields"), contains("admin_notification", "block"));
        assertThat(body.get("stage"), is(notNullValue()));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetBruteForceConfiguration() throws Exception {
        Request<BruteForceConfiguration> request = api.attackProtection().getBruteForceConfiguration();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(BRUTE_FORCE_CONFIGURATION, 200);
        BruteForceConfiguration response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/attack-protection/brute-force-protection"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getEnabled(), is(false));
        assertThat(response.getMode(), is("count_per_identifier_and_ip"));
        assertThat(response.getMaxAttempts(), is(10));
        assertThat(response.getShields(), is(notNullValue()));
        assertThat(response.getShields().size(), is(2));
        assertThat(response.getShields(), contains("block", "user_notification"));
        assertThat(response.getAllowList(), is(notNullValue()));
        assertThat(response.getAllowList().size(), is(2));
        assertThat(response.getAllowList(), contains("143.204.0.105",
            "2600:9000:208f:ca00:d:f5f5:b40:93a1"));
    }

    @Test
    public void shouldUpdateBruteForceConfiguration() throws Exception {
        List<String> allowList = Arrays.asList("123", "456");
        List<String> shields = Arrays.asList("block", "user_notification");
        String mode = "count_per_identifier_and_ip";

        BruteForceConfiguration configuration = new BruteForceConfiguration();
        configuration.setEnabled(true);
        configuration.setMaxAttempts(5);
        configuration.setMode(mode);
        configuration.setAllowList(allowList);
        configuration.setShields(shields);

        Request<BruteForceConfiguration> request = api.attackProtection().updateBruteForceConfiguration(configuration);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(BRUTE_FORCE_CONFIGURATION, 200);
        BruteForceConfiguration response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/attack-protection/brute-force-protection"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);

        assertThat(body.size(), is(5));
        assertThat(body.get("enabled"), is(true));
        assertThat(body.get("max_attempts"), is(5));
        assertThat(body.get("mode"), is(mode));
        assertThat(body.get("shields"), is(shields));
        assertThat(body.get("allowlist"), is(allowList));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetSuspiciousIPThrottlingConfig() throws Exception {
        Request<SuspiciousIPThrottlingConfiguration> request = api.attackProtection().getSuspiciousIPThrottlingConfiguration();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SUSPICIOUS_IP_THROTTLING_CONFIGURATION, 200);
        SuspiciousIPThrottlingConfiguration response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/attack-protection/suspicious-ip-throttling"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getEnabled(), is(false));
        assertThat(response.getShields(), is(notNullValue()));
        assertThat(response.getShields().size(), is(2));
        assertThat(response.getShields(), contains("block", "admin_notification"));
        assertThat(response.getAllowList(), is(notNullValue()));
        assertThat(response.getAllowList().size(), is(2));
        assertThat(response.getAllowList(), contains("143.204.0.105",
            "2600:9000:208f:ca00:d:f5f5:b40:93a1"));
        assertThat(response.getStage(), is(notNullValue()));
        assertThat(response.getStage().getPreLoginStage(), is(notNullValue()));
        assertThat(response.getStage().getPreLoginStage().getMaxAttempts(), is(100));
        assertThat(response.getStage().getPreLoginStage().getRate(), is(864000));
        assertThat(response.getStage().getPreUserRegistrationStage(), is(notNullValue()));
        assertThat(response.getStage().getPreUserRegistrationStage().getMaxAttempts(), is(50));
        assertThat(response.getStage().getPreUserRegistrationStage().getRate(), is(1728000));
    }

    @Test
    public void shouldUpdateSuspiciousIPTThrottlingConfig() throws Exception {
        List<String> allowList = Arrays.asList("123", "456");
        List<String> shields = Arrays.asList("block", "user_notification");

        Stage stage = new Stage();
        StageEntry preLogin = new StageEntry();
        preLogin.setRate(10);
        preLogin.setMaxAttempts(11);
        StageEntry preReg = new StageEntry();
        preReg.setRate(12);
        preReg.setMaxAttempts(13);
        stage.setPreLoginStage(preLogin);
        stage.setPreUserRegistrationStage(preLogin);

        SuspiciousIPThrottlingConfiguration configuration = new SuspiciousIPThrottlingConfiguration();
        configuration.setEnabled(true);
        configuration.setAllowList(allowList);
        configuration.setShields(shields);
        configuration.setStage(stage);

        Request<SuspiciousIPThrottlingConfiguration> request = api.attackProtection().updateSuspiciousIPThrottlingConfiguration(configuration);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(BRUTE_FORCE_CONFIGURATION, 200);
        SuspiciousIPThrottlingConfiguration response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/attack-protection/suspicious-ip-throttling"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);

        assertThat(body.size(), is(4));
        assertThat(body.get("enabled"), is(true));
        assertThat(body.get("shields"), is(shields));
        assertThat(body.get("allowlist"), is(allowList));
        assertThat(body.get("stage"), is(notNullValue()));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void updateBreachedPasswordSettingsShouldThrowWithNullParam() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("breached password");
        api.attackProtection().updateBreachedPasswordSettings(null);
    }

    @Test
    public void updateBruteForceConfigurationShouldThrowWithNullParam() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("configuration");
        api.attackProtection().updateBruteForceConfiguration(null);
    }

    @Test
    public void updateSuspiciousIPThrottlingConfigurationShouldThrowWithNullParam() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("configuration");
        api.attackProtection().updateSuspiciousIPThrottlingConfiguration(null);
    }
}
