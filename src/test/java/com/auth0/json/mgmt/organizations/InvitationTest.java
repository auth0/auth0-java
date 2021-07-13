package com.auth0.json.mgmt.organizations;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class InvitationTest extends JsonTest<Invitation> {

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\n" +
            "  \"id\": \"inv_1\",\n" +
            "  \"client_id\": \"client-id\",\n" +
            "  \"inviter\": {\n" +
            "    \"name\": \"timmy\"\n" +
            "  },\n" +
            "  \"invitee\": {\n" +
            "    \"email\": \"timmy@domain.com\"\n" +
            "  },\n" +
            "  \"invitation_url\": \"https://somedomain.com\",\n" +
            "  \"ticket_id\": \"ticket-id\",\n" +
            "  \"created_at\": \"2021-03-31T19:08:40.295Z\",\n" +
            "  \"expires_at\": \"2021-04-07T19:08:40.295Z\",\n" +
            "  \"organization_id\": \"org_abc\"\n" +
            "}\n";

        Invitation invitation = fromJSON(json, Invitation.class);

        assertThat(invitation, is(notNullValue()));
        assertThat(invitation.getId(), is("inv_1"));
        assertThat(invitation.getClientId(), is("client-id"));
        assertThat(invitation.getInviter(), is(notNullValue()));
        assertThat(invitation.getInviter().getName(), is("timmy"));
        assertThat(invitation.getInvitee(), is(notNullValue()));
        assertThat(invitation.getInvitee().getEmail(), is("timmy@domain.com"));
        assertThat(invitation.getInvitationUrl(), is("https://somedomain.com"));
        assertThat(invitation.getTicketId(), is("ticket-id"));
        assertThat(invitation.getCreatedAt(), is(parseJSONDate("2021-03-31T19:08:40.295Z")));
        assertThat(invitation.getExpiresAt(), is(parseJSONDate("2021-04-07T19:08:40.295Z")));
        assertThat(invitation.getOrganizationId(), is("org_abc"));
    }

    @Test
    public void shouldSerialize() throws Exception {
        Map<String, Object> appMetadata = Collections.singletonMap("app1", "val1");
        Map<String, Object> userMetadata = Collections.singletonMap("user1", "val1");
        List<String> rolesList = Arrays.asList("role-1", "role-2");

        Invitation invitation = new Invitation(new Inviter("name"), new Invitee("joe@domain.com"), "client-id");
        invitation.setSendInvitationEmail(true);
        invitation.setAppMetadata(appMetadata);
        invitation.setUserMetadata(userMetadata);
        invitation.setConnectionId("connId");
        invitation.setRoles(rolesList);
        invitation.setTtlInSeconds(41);

        String serialized = toJSON(invitation);

        assertThat(serialized, is(notNullValue()));
        System.out.println(serialized);
        assertThat(serialized, JsonMatcher.hasEntry("client_id", "client-id"));
        assertThat(serialized, JsonMatcher.hasEntry("ttl_sec", 41));
        assertThat(serialized, JsonMatcher.hasEntry("connection_id", "connId"));
        assertThat(serialized, JsonMatcher.hasEntry("send_invitation_email", true));
        assertThat(serialized, JsonMatcher.hasEntry("roles", rolesList));
        assertThat(serialized, JsonMatcher.hasEntry("app_metadata", appMetadata));
        assertThat(serialized, JsonMatcher.hasEntry("user_metadata", userMetadata));
        assertThat(serialized, JsonMatcher.hasEntry("inviter", is(notNullValue())));
        assertThat(serialized, JsonMatcher.hasEntry("invitee", is(notNullValue())));
    }
}
