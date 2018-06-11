package com.auth0.json.mgmt.users;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
class UsersPageDeserializer extends PageDeserializer<UsersPage, User> {

    UsersPageDeserializer() {
        super(User.class, "users");
    }

    @Override
    protected UsersPage createPage(List<User> items) {
        return new UsersPage(items);
    }

    @Override
    protected UsersPage createPage(Integer start, Integer length, Integer total, Integer limit, List<User> items) {
        return new UsersPage(start, length, total, limit, items);
    }

}
