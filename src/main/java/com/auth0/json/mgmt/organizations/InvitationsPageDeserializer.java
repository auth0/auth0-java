package com.auth0.json.mgmt.organizations;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a paged response into its {@linkplain InvitationsPage} representation.
 */
public class InvitationsPageDeserializer extends PageDeserializer<InvitationsPage, Invitation> {

    protected InvitationsPageDeserializer() {
        super(Invitation.class, "invitations");
    }

    @Override
    protected InvitationsPage createPage(List<Invitation> items) {
        return new InvitationsPage(items);
    }

    @Override
    protected InvitationsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Invitation> items) {
        return new InvitationsPage(start, length, total, limit, items);
    }
}
