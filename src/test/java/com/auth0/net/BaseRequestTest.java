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

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

// FIXME: These test require mocking of the final class okhttp3.Response. To do so
//  an opt-in incubating Mockito feature is used, for more information see:
//  https://github.com/mockito/mockito/wiki/What%27s-new-in-Mockito-2#mock-the-unmockable-opt-in-mocking-of-final-classesmethods
public class BaseRequestTest {

    private Response response;
    private Call call;
    private OkHttpClient client;

    @Before
    public void setUp() throws Exception {
        response = mock(Response.class);

        call = mock(Call.class);
        when(call.execute()).thenReturn(response);

        client = mock(OkHttpClient.class);
        when(client.newCall(any())).thenReturn(call);
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
        verify(response, times(1)).close();
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
        verify(response, times(1)).close();
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
        verify(response, times(1)).close();
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
        verify(response, times(1)).close();
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
