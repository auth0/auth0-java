package com.auth0.net;

interface CustomizableRequest<T> extends Request<T> {

    CustomizableRequest<T> addHeader(String name, String value);

    CustomizableRequest<T> addParameter(String name, Object value);

    CustomizableRequest<T> setBody(Object body);
}
