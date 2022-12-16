package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import org.junit.Test;
import static org.junit.Assert.assertThrows;

public class RequestTest {

    @Test
    public void defaultImplementationShouldThrow() {
        assertThrows("executeAsync",
            UnsupportedOperationException.class,
            new Request<String>() {
                @Override
                public Response<String> execute() throws Auth0Exception {
                    return null;
                }

                @Override
                public Request<String> addHeader(String name, String value) {
                    return null;
                }

                @Override
                public Request<String> addParameter(String name, Object value) {
                    return null;
                }

                @Override
                public Request<String> setBody(Object body) {
                    return null;
                }
            }::executeAsync);
    }
}
