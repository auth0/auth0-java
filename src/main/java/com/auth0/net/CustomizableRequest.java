package com.auth0.net;

interface CustomizableRequest<T> extends Request<T> {

    void addHeader(String name, String value);

    void addParameter(String name, Object value);
}
