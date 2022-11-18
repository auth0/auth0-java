package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import com.auth0.net.client.HttpMethod;
import com.auth0.net.client.HttpRequestBody;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertThrows;

public class RequestTest {

    @Test
    public void defaultImplementationShouldThrow() {
        assertThrows("executeAsync",
            UnsupportedOperationException.class,
            new Request<String>() {
                @Override
                public HttpRequestBody getBody() {
                    return null;
                }

                @Override
                public String getUrl() {
                    return null;
                }

                @Override
                public HttpMethod getMethod() {
                    return null;
                }

                @Override
                public Map<String, String> getHeaders() {
                    return null;
                }

                @Override
                public Response<String> execute() throws Auth0Exception {
                    return null;
                }
            }::executeAsync);
    }
}
