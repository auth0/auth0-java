package com.auth0.json.mgmt.actions;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a paged response into its {@linkplain ActionsPage} representation.
 */
public class BindingsPageDeserializer extends PageDeserializer<BindingsPage, Binding> {

    protected BindingsPageDeserializer() {
        super(Binding.class, "bindings");
    }

    @Override
    protected BindingsPage createPage(List<Binding> items) {
        return new BindingsPage(items);
    }

    @Override
    protected BindingsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Binding> items) {
        return new BindingsPage(start, length, total, limit, items);
    }
}
