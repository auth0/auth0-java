package com.auth0.json.mgmt.organizations;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a paged response into its {@linkplain MembersPage} representation.
 */
public class MembersPageDeserializer extends PageDeserializer<MembersPage, Member> {

    protected MembersPageDeserializer() {
        super(Member.class, "members");
    }

    @Override
    protected MembersPage createPage(List<Member> items) {
        return new MembersPage(items);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected MembersPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Member> items) {
        return new MembersPage(start, length, total, limit, items);
    }

    @Override
    protected MembersPage createPage(Integer start, Integer length, Integer total, Integer limit, String next, List<Member> items) {
        return new MembersPage(start, length, total, limit, next, items);
    }
}
