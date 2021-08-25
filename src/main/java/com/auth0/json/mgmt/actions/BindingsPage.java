package com.auth0.json.mgmt.actions;

import com.auth0.json.mgmt.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents a page of an action trigger binding.
 * @see Binding
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = BindingsPageDeserializer.class)
public class BindingsPage extends Page<Binding> {
    public BindingsPage(List<Binding> items) {
        super(items);
    }

    public BindingsPage(Integer start, Integer length, Integer total, Integer limit, List<Binding> items) {
        super(start, length, total, limit, items);
    }

    public BindingsPage(Integer start, Integer length, Integer total, Integer limit, String next, List<Binding> items) {
        super(start, length, total, limit, next, items);
    }
}
