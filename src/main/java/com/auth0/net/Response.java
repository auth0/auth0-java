package com.auth0.net;

import java.util.Map;

public interface Response<T> {

    Map<String, String> getHeaders();

    T getBody();

    int getStatusCode();
}
