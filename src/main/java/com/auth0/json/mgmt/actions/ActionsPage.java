package com.auth0.json.mgmt.actions;

import com.auth0.json.mgmt.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents a page of an Action.
 * @see Action
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = ActionsPageDeserializer.class)
public class ActionsPage extends Page<Action> {
    public ActionsPage(List<Action> items) {
        super(items);
    }

    public ActionsPage(Integer start, Integer length, Integer total, Integer limit, List<Action> items) {
        super(start, length, total, limit, items);
    }

    public ActionsPage(Integer start, Integer length, Integer total, Integer limit, String next, List<Action> items) {
        super(start, length, total, limit, next, items);
    }
}
