package com.auth0.json.mgmt.userAttributeProfiles;

import com.auth0.json.mgmt.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = UserAttributeProfilePageDeserializer.class)
public class UserAttributeProfilePage extends Page<UserAttributeProfile> {
    public UserAttributeProfilePage(List<UserAttributeProfile> items) {
        super(items);
    }

    protected UserAttributeProfilePage(Integer start, Integer length, Integer total, Integer limit, List<UserAttributeProfile> items) {
        super(start, length, total, limit, items);
    }

    protected UserAttributeProfilePage(Integer start, Integer length, Integer total, Integer limit, String next, List<UserAttributeProfile> items){
        super(start, length, total, limit, next, items);
    }
}
