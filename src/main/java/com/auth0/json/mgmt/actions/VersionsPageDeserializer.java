package com.auth0.json.mgmt.actions;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

public class VersionsPageDeserializer extends PageDeserializer<VersionsPage, Version> {

    protected VersionsPageDeserializer() {
        super(Version.class, "versions");
    }

    @Override
    protected VersionsPage createPage(List<Version> items) {
        return new VersionsPage(items);
    }

    @Override
    protected VersionsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Version> items) {
        return new VersionsPage(start, length, total, limit, items);
    }
}
