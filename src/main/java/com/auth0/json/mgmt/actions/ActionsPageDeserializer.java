package com.auth0.json.mgmt.actions;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a paged response into its {@linkplain ActionsPage} representation.
 */
public class ActionsPageDeserializer extends PageDeserializer<ActionsPage, Action> {

    protected ActionsPageDeserializer() {
        super(Action.class, "actions");
    }

    @Override
    protected ActionsPage createPage(List<Action> items) {
        return new ActionsPage(items);
    }

    @Override
    protected ActionsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Action> items) {
        return new ActionsPage(start, length, total, limit, items);
    }
}
