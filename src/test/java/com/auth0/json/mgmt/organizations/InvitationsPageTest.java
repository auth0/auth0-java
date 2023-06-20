package com.auth0.json.mgmt.organizations;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class InvitationsPageTest extends JsonTest<InvitationsPage> {

    private static final String jsonWithoutTotals = "[\n" +
        "  {\n" +
        "    \"id\": \"uinv_Q3lgZ0a99aY7Rqs3\",\n" +
        "    \"client_id\": \"klCnbSD1U1Yd0333dvEAFMwDoOX5bfsb\",\n" +
        "    \"inviter\": {\n" +
        "      \"name\": \"dave!\"\n" +
        "    },\n" +
        "    \"invitee\": {\n" +
        "      \"email\": \"dave@domain.com\"\n" +
        "    },\n" +
        "    \"invitation_url\": \"https://some-domain.auth0.com/login?invitation=U0o1uoXAl1VPkKbZlaRWEWJadkeed1E&organization=org_W3OHp07dfbjhHuWK&organization_name=timmy-org\",\n" +
        "    \"ticket_id\": \"U0o1uoXAl1VPkKbZla0a833soMUMOo1E\",\n" +
        "    \"created_at\": \"2021-03-31T19:08:40.295Z\",\n" +
        "    \"expires_at\": \"2021-04-07T19:08:40.295Z\",\n" +
        "    \"organization_id\": \"org_W30s833MRbjhHuWK\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"uinv_Q3lgZ0a99aY7Rqs3\",\n" +
        "    \"client_id\": \"klCnbSD1U1Yd0333dvEAFMwDoOX5bfsb\",\n" +
        "    \"inviter\": {\n" +
        "      \"name\": \"jim.anderson@auth0.com\"\n" +
        "    },\n" +
        "    \"invitee\": {\n" +
        "      \"email\": \"jim.anderson+android@auth0.com\"\n" +
        "    },\n" +
        "    \"app_metadata\": {},\n" +
        "    \"user_metadata\": {},\n" +
        "    \"invitation_url\": \"https://some-domain.auth0.com/login?invitation=U0o1uoXAl1VPkKbZlaRWEWJadkeed1E&organization=org_W3OHp07dfbjhHuWK&organization_name=timmy-org\",\n" +
        "    \"ticket_id\": \"U0o1uoXAl1VPkK0a940a833soMUMOo1E\",\n" +
        "    \"created_at\": \"2021-03-31T17:59:23.132Z\",\n" +
        "    \"expires_at\": \"2021-04-07T17:59:23.132Z\",\n" +
        "    \"organization_id\": \"org_W30s833MRbjhHuWK\"\n" +
        "  }\n" +
        "]\n";

    private static final String jsonWithTotals = "{\n" +
        "  \"invitations\": [\n" +
        "    {\n" +
        "      \"id\": \"uinv_Q3lgZ0a99aY7Rqs3\",\n" +
        "      \"client_id\": \"klCnbSD1U1Yd0333dvEAFMwDoOX5bfsb\",\n" +
        "      \"inviter\": {\n" +
        "        \"name\": \"dave!\"\n" +
        "      },\n" +
        "      \"invitee\": {\n" +
        "        \"email\": \"dave@domain.com\"\n" +
        "      },\n" +
        "      \"invitation_url\": \"https://some-domain.auth0.com/login?invitation=U0o1uoXAl1VPkKbZlaRWEWJadkeed1E&organization=org_W3OHp07dfbjhHuWK&organization_name=timmy-org\",\n" +
        "      \"ticket_id\": \"U0o1uoXAl1VPkKbZla0a833soMUMOo1E\",\n" +
        "      \"created_at\": \"2021-03-31T19:08:40.295Z\",\n" +
        "      \"expires_at\": \"2021-04-07T19:08:40.295Z\",\n" +
        "      \"organization_id\": \"org_W30s833MRbjhHuWK\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"uinv_Q3lgZ0a99aY7Rqs3\",\n" +
        "      \"client_id\": \"klCnbSD1U1Yd0333dvEAFMwDoOX5bfsb\",\n" +
        "      \"inviter\": {\n" +
        "        \"name\": \"jim.anderson@auth0.com\"\n" +
        "      },\n" +
        "      \"invitee\": {\n" +
        "        \"email\": \"jim.anderson+android@auth0.com\"\n" +
        "      },\n" +
        "      \"app_metadata\": {},\n" +
        "      \"user_metadata\": {},\n" +
        "      \"invitation_url\": \"https://some-domain.auth0.com/login?invitation=U0o1uoXAl1VPkKbZlaRWEWJadkeed1E&organization=org_W3OHp07dfbjhHuWK&organization_name=timmy-org\",\n" +
        "      \"ticket_id\": \"U0o1uoXAl1VPkK0a940a833soMUMOo1E\",\n" +
        "      \"created_at\": \"2021-03-31T17:59:23.132Z\",\n" +
        "      \"expires_at\": \"2021-04-07T17:59:23.132Z\",\n" +
        "      \"organization_id\": \"org_W30s833MRbjhHuWK\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"start\": 0,\n" +
        "  \"limit\": 4\n" +
        "}\n";

    @Test
    public void shouldDeserializeWithoutTotals() throws Exception {
        InvitationsPage page = fromJSON(jsonWithoutTotals, InvitationsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(nullValue()));
        assertThat(page.getLength(), is(nullValue()));
        assertThat(page.getTotal(), is(nullValue()));
        assertThat(page.getLimit(), is(nullValue()));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(2));
    }

    @Test
    public void shouldDeserializeWithTotals() throws Exception {
        InvitationsPage page = fromJSON(jsonWithTotals, InvitationsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getStart(), is(0));
        assertThat(page.getLimit(), is(4));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(2));
    }
}
