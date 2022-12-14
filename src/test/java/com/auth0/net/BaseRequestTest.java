package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import com.auth0.net.client.Auth0HttpRequest;
import com.auth0.net.client.Auth0HttpResponse;
import com.auth0.net.client.DefaultHttpClient;
import com.auth0.net.client.HttpMethod;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;

// FIXME: These test require mocking of the final class okhttp3.Response. To do so
//  an opt-in incubating Mockito feature is used, for more information see:
//  https://github.com/mockito/mockito/wiki/What%27s-new-in-Mockito-2#mock-the-unmockable-opt-in-mocking-of-final-classesmethods
//  This issue can be tracked to see if/when this feature will become standard for Mockito (perhaps Mockito 4):
//  https://github.com/mockito/mockito/issues/1728
public class BaseRequestTest {

    @Test
    public void handlesIOException() throws Exception {
        DefaultHttpClient client = mock(DefaultHttpClient.class);
        when(client.sendRequest(any())).thenThrow(IOException.class);

        BaseRequest<String> req = new BaseRequest<String>(client) {
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

            @Override
            protected Auth0HttpRequest createRequest() throws Auth0Exception {
                return null;
            }

            @Override
            protected String parseResponseBody(Auth0HttpResponse response) throws Auth0Exception {
                return null;
            }
        };

        Auth0Exception exception = assertThrows(Auth0Exception.class, req::execute);
        assertThat(exception.getCause(), is(instanceOf(IOException.class)));
    }

    @Test
    public void asyncHandlesIOExceptionWhenCreatingRequest() throws Exception {
        DefaultHttpClient client = DefaultHttpClient.newBuilder().build();

        BaseRequest<String> req = new BaseRequest<String>(client) {
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

            @Override
            protected Auth0HttpRequest createRequest() throws Auth0Exception {
                throw new Auth0Exception("error", new IOException("boom"));
            }

            @Override
            protected String parseResponseBody(Auth0HttpResponse response) throws Auth0Exception {
                return null;
            }
        };

        Auth0HttpRequest a0Request = Auth0HttpRequest.newBuilder("https://foo.com", HttpMethod.GET).build();
        CompletableFuture<Response<String>> future = req.executeAsync();
        ExecutionException e = assertThrows(ExecutionException.class, future::get);
        assertThat(e.getCause(), is(instanceOf(IOException.class)));
    }
}
