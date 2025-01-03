package com.auth0.json.mgmt.selfserviceprofiles;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SsoAccessTicketResponseTest extends JsonTest<SsoAccessTicketResponse> {

    private final static String SELF_SERVICE_PROFILE_SSO_TICKET_JSON = "src/test/resources/mgmt/self_service_profile_sso_ticket.json";

    @Test
    public void deserialize() throws Exception {
        SsoAccessTicketResponse deserialized = fromJSON(readTextFile(SELF_SERVICE_PROFILE_SSO_TICKET_JSON), SsoAccessTicketResponse.class);

        assertThat(deserialized.getTicket(), is("https://example.auth0.com/self-service/connections-flow?ticket=ticket-1234"));
    }

    @Test
    public void serialize() throws Exception {
        SsoAccessTicketResponse ssoAccessTicketResponse = new SsoAccessTicketResponse();
        ssoAccessTicketResponse.setTicket("https://example.auth0.com/self-service/connections-flow?ticket=ticket-1234");

        String serialized = toJSON(ssoAccessTicketResponse);
        assertThat(serialized, is(notNullValue()));

        assertThat(serialized, hasEntry("ticket", "https://example.auth0.com/self-service/connections-flow?ticket=ticket-1234"));
    }
}
