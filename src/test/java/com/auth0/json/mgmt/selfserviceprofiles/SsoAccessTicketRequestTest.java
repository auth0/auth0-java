package com.auth0.json.mgmt.selfserviceprofiles;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SsoAccessTicketRequestTest extends JsonTest<SsoAccessTicketRequest> {
    private final static String SELF_SERVICE_PROFILE_SSO_ACCESS_TICKET_REQUEST_JSON = "src/test/resources/mgmt/self_service_profile_sso_ticket_request.json";

    @Test
    public void deserialize() throws Exception {
        SsoAccessTicketRequest deserialized = fromJSON(readTextFile(SELF_SERVICE_PROFILE_SSO_ACCESS_TICKET_REQUEST_JSON), SsoAccessTicketRequest.class);

        assertThat(deserialized.getConnectionConfig(), is(notNullValue()));
        assertThat(deserialized.getEnabledClients().get(0), is("client-1"));

        assertThat(deserialized.getEnabledOrganizations().get(0).getOrganizationId(), is("org_1"));
        assertThat(deserialized.getEnabledOrganizations().get(0).isAssignMembershipOnLogin(), is(true));
        assertThat(deserialized.getEnabledOrganizations().get(0).isShowAsButton(), is(true));

        assertThat(deserialized.getTtlSec(), is(0));

        assertThat(deserialized.getDomainAliasesConfig().getDomainVerification(), is("none"));
    }

    @Test
    public void serialize() throws Exception {

        Map<String, Object> connectionConfig = new HashMap<>();
        connectionConfig.put("name", "okta");
        connectionConfig.put("display_name", "okta connection");
        connectionConfig.put("is_domain_connection", true);
        connectionConfig.put("show_as_button", true);
        connectionConfig.put("metadata", new HashMap<>());

        Map<String, Object> idpInitiated = new HashMap<>();
        idpInitiated.put("enabled", true);
        idpInitiated.put("client_id", "client-1");
        idpInitiated.put("client_protocol", "oauth2");
        idpInitiated.put("client_authorizequery", "response_type=code&scope=openid%20profile%20email");

        Map<String, Object> options = new HashMap<>();
        options.put("idpinitiated", idpInitiated);
        options.put("icon_url", "https://cdn.auth0.com/connections/okta.png");
        options.put("domain_aliases", new ArrayList<String>() {{
            add("acme.corp");
        }});

        connectionConfig.put("options", options);

        SsoAccessTicketRequest ssoAccessTicketRequest = new SsoAccessTicketRequest();

        ssoAccessTicketRequest.setConnectionConfig(connectionConfig);
        ssoAccessTicketRequest.setEnabledClients(new ArrayList<String>() {{
            add("client-1");
        }});

        EnabledOrganizations enabledOrganizations = new EnabledOrganizations();
        enabledOrganizations.setOrganizationId("org_1");
        enabledOrganizations.setAssignMembershipOnLogin(true);
        enabledOrganizations.setShowAsButton(true);

        ssoAccessTicketRequest.setEnabledOrganizations(new ArrayList<EnabledOrganizations>() {{
            add(enabledOrganizations);
        }});

        ssoAccessTicketRequest.setTtlSec(0);

        ssoAccessTicketRequest.setDomainAliasesConfig(new DomainAliasesConfig("none"));

        String serialized = toJSON(ssoAccessTicketRequest);
        assertThat(ssoAccessTicketRequest, is(notNullValue()));
        assertThat(serialized, containsString("{\"connection_config\":{\"metadata\":{},\"is_domain_connection\":true,\"show_as_button\":true,\"name\":\"okta\",\"options\":{\"icon_url\":\"https://cdn.auth0.com/connections/okta.png\",\"domain_aliases\":[\"acme.corp\"],\"idpinitiated\":{\"client_authorizequery\":\"response_type=code&scope=openid%20profile%20email\",\"client_protocol\":\"oauth2\",\"enabled\":true,\"client_id\":\"client-1\"}},\"display_name\":\"okta connection\"},\"enabled_clients\":[\"client-1\"],\"enabled_organizations\":[{\"organization_id\":\"org_1\",\"assign_membership_on_login\":true,\"show_as_button\":true}],\"ttl_sec\":0,\"domain_aliases_config\":{\"domain_verification\":\"none\"}}"));
        assertThat(serialized, containsString("\"enabled_clients\":[\"client-1\"]"));
        assertThat(serialized, containsString("\"enabled_organizations\":[{\"organization_id\":\"org_1\",\"assign_membership_on_login\":true,\"show_as_button\":true}]"));
        assertThat(serialized, containsString("\"ttl_sec\":0"));
        assertThat(serialized, containsString("\"domain_aliases_config\":{\"domain_verification\":\"none\"}"));

    }
}
