package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = UsersPageDeserializer.class)
public class UsersPage extends Page<User> {


    public UsersPage(List<User> items) {
        super(items);
    }

    public UsersPage(Integer start, Integer length, Integer total, Integer limit, List<User> items) {
        super(start, length, total, limit, items);
    }

}