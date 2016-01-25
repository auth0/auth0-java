package com.auth0.util;

public interface Metrics {
    String NAME_KEY = "name";
    String VERSION_KEY = "version";
    String HEADER_NAME = "Auth0-Client";

    void usingLibrary(String name, String version);
    String getValue();
}
