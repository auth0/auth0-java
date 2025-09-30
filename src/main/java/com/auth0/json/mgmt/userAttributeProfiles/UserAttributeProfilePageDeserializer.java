package com.auth0.json.mgmt.userAttributeProfiles;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;


@SuppressWarnings({"unused", "WeakerAccess"})
class UserAttributeProfilePageDeserializer extends PageDeserializer<UserAttributeProfilePage, UserAttributeProfile> {
    UserAttributeProfilePageDeserializer() {
        super(UserAttributeProfile.class, "user_attribute_profiles");
    }

    @Override
    protected UserAttributeProfilePage createPage(List<UserAttributeProfile> items) {
        return new UserAttributeProfilePage(items);
    }

    @Override
    protected UserAttributeProfilePage createPage(Integer start, Integer length, Integer total, Integer limit, List<UserAttributeProfile> items) {
        return new UserAttributeProfilePage(start, length, total, limit, items);
    }

    @Override
    protected UserAttributeProfilePage createPage(Integer start, Integer length, Integer total, Integer limit, String next, List<UserAttributeProfile> items) {
        return new UserAttributeProfilePage(start, length, total, limit, next, items);
    }
}
