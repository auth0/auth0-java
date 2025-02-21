package com.auth0.json.mgmt.selfserviceprofiles;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SsoAccessTicketRequestTest extends JsonTest<SsoAccessTicketRequest> {
    private final static String SELF_SERVICE_PROFILE_SSO_ACCESS_TICKET_REQUEST_JSON = "src/test/resources/mgmt/self_service_profile_sso_ticket_request.json";

    @Test
    public void deserialize() throws Exception {
        SsoAccessTicketRequest deserialized = fromJSON(readTextFile(SELF_SERVICE_PROFILE_SSO_ACCESS_TICKET_REQUEST_JSON), SsoAccessTicketRequest.class);

        assertThat(deserialized.getConnectionConfig().getName(), is("sso-test1"));
        assertThat(deserialized.getConnectionConfig().getDisplayName(), is("sso-test1"));
        assertThat(deserialized.getConnectionConfig().isDomainConnection(), is(true));
        assertThat(deserialized.getConnectionConfig().isShowAsButton(), is(true));
        assertThat(deserialized.getConnectionConfig().getOptions().getIconUrl(), is("url"));
        assertThat(deserialized.getConnectionConfig().getOptions().getDomainAliases().get(0), is("acme.corp"));
        assertThat(deserialized.getConnectionConfig().getOptions().getIdpinitiated().isEnabled(), is(true));
        assertThat(deserialized.getConnectionConfig().getOptions().getIdpinitiated().getClientId(), is("client-id"));
        assertThat(deserialized.getConnectionConfig().getOptions().getIdpinitiated().getClientProtocol(), is("client-protocol"));
        assertThat(deserialized.getConnectionConfig().getOptions().getIdpinitiated().getClientAuthorizequery(), is("client-authorizequery"));

        assertThat(deserialized.getEnabledClients().get(0), is("client-id"));

        assertThat(deserialized.getEnabledOrganizations().get(0).getOrganizationId(), is("org_1"));
        assertThat(deserialized.getEnabledOrganizations().get(0).isAssignMembershipOnLogin(), is(true));
        assertThat(deserialized.getEnabledOrganizations().get(0).isShowAsButton(), is(true));

        assertThat(deserialized.getTtlSec(), is(0));

        assertThat(deserialized.getDomainAliasesConfig().getDomainVerification(), is("domain-verification"));
    }

    @Test
    public void serialize() throws Exception {
        SsoAccessTicketRequest ssoAccessTicketRequest = new SsoAccessTicketRequest();
        ConnectionConfig connectionConfig = new ConnectionConfig();
        connectionConfig.setName("sso-test1");
        connectionConfig.setDisplayName("sso-test1");
        connectionConfig.setDomainConnection(true);
        connectionConfig.setShowAsButton(true);

        Options options = new Options();
        options.setIconUrl("url");

        options.setDomainAliases(new ArrayList<String>() {{
            add("acme.corp");
        }});

        Idpinitiated idpinitiated = new Idpinitiated();
        idpinitiated.setEnabled(true);
        idpinitiated.setClientId("client-id");
        idpinitiated.setClientProtocol("client-protocol");
        idpinitiated.setClientAuthorizequery("client-authorizequery");
        options.setIdpinitiated(idpinitiated);

        connectionConfig.setOptions(options);

        ssoAccessTicketRequest.setConnectionConfig(connectionConfig);
        ssoAccessTicketRequest.setEnabledClients(new ArrayList<String>() {{
            add("client-id");
        }});

        EnabledOrganizations enabledOrganizations = new EnabledOrganizations();
        enabledOrganizations.setOrganizationId("org_1");
        enabledOrganizations.setAssignMembershipOnLogin(true);
        enabledOrganizations.setShowAsButton(true);

        ssoAccessTicketRequest.setEnabledOrganizations(new ArrayList<EnabledOrganizations>() {{
            add(enabledOrganizations);
        }});

        ssoAccessTicketRequest.setTtlSec(0);

        ssoAccessTicketRequest.setDomainAliasesConfig(new DomainAliasesConfig("domain-verification"));

        String serialized = toJSON(ssoAccessTicketRequest);
        assertThat(ssoAccessTicketRequest, is(notNullValue()));


        assertThat(serialized, containsString("\"name\":\"sso-test1\""));
        assertThat(serialized, containsString("\"display_name\":\"sso-test1\""));
        assertThat(serialized, containsString("\"is_domain_connection\":true"));
        assertThat(serialized, containsString("\"show_as_button\":true"));
        assertThat(serialized, containsString("\"icon_url\":\"url\""));
        assertThat(serialized, containsString("\"domain_aliases\":[\"acme.corp\"]"));
        assertThat(serialized, containsString("\"idpinitiated\":{\"enabled\":true,\"client_id\":\"client-id\",\"client_protocol\":\"client-protocol\",\"client_authorizequery\":\"client-authorizequery\"}"));
        assertThat(serialized, containsString("\"enabled_clients\":[\"client-id\"]"));
        assertThat(serialized, containsString("\"enabled_organizations\":[{\"organization_id\":\"org_1\",\"assign_membership_on_login\":true,\"show_as_button\":true}]"));
        assertThat(serialized, containsString("\"ttl_sec\":0"));
        assertThat(serialized, containsString("\"domain_aliases_config\":{\"domain_verification\":\"domain-verification\"}"));

    }
}
