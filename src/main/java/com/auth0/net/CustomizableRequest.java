package com.auth0.net;

public interface CustomizableRequest {

    void addHeader(String name, String value);

    void addParameter(String name, String value);
}
