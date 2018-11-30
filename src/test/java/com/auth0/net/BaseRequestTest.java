package com.auth0.net;

import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RateLimitException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BaseRequestTest {

    private Response response;
    private Call call;
    private OkHttpClient client;

    @Before
    public void setUp() throws Exception {
        response = Mockito.mock(Response.class);

        call = Mockito.mock(Call.class);
        Mockito.when(call.execute()).thenReturn(response);

        client = Mockito.mock(OkHttpClient.class);
        Mockito.when(client.newCall(Mockito.any())).thenReturn(call);
    }

    @Test
    public void alwaysCloseResponseOnSuccessfulRequest() throws Exception {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponse(Response response) throws Auth0Exception {
                    return "";
                }
            }.execute();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(nullValue()));
        Mockito.verify(response, Mockito.times(1)).close();
    }

    @Test
    public void alwaysCloseResponseOnRateLimitException() throws Exception {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponse(Response response) throws Auth0Exception {
                    throw new RateLimitException(-1, -1, -1);
                }
            }.execute();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(RateLimitException.class)));
        Mockito.verify(response, Mockito.times(1)).close();
    }

    @Test
    public void alwaysCloseResponseOnAPIException() throws Exception {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponse(Response response) throws Auth0Exception {
                    throw new APIException(new HashMap<>(), 500);
                }
            }.execute();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(APIException.class)));
        Mockito.verify(response, Mockito.times(1)).close();
    }

    @Test
    public void alwaysCloseResponseOnAuth0Exception() throws Exception {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponse(Response response) throws Auth0Exception {
                    throw new Auth0Exception("Auth0Exception");
                }
            }.execute();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(Auth0Exception.class)));
        Mockito.verify(response, Mockito.times(1)).close();
    }

    private abstract class MockBaseRequest<String> extends BaseRequest {
        MockBaseRequest(OkHttpClient client) {
            super(client);
        }

        @Override
        protected Request createRequest() {
            return null;
        }
    }

}
