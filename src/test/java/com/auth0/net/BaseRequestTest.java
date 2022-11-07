package com.auth0.net;

import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RateLimitException;
import okhttp3.Request;
import okhttp3.*;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

// FIXME: These test require mocking of the final class okhttp3.Response. To do so
//  an opt-in incubating Mockito feature is used, for more information see:
//  https://github.com/mockito/mockito/wiki/What%27s-new-in-Mockito-2#mock-the-unmockable-opt-in-mocking-of-final-classesmethods
//  This issue can be tracked to see if/when this feature will become standard for Mockito (perhaps Mockito 4):
//  https://github.com/mockito/mockito/issues/1728
public class BaseRequestTest {

    private okhttp3.Response response;
    private Call call;
    private OkHttpClient client;

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        response = mock(okhttp3.Response.class);

        call = mock(Call.class);
        when(call.execute()).thenReturn(response);

        client = mock(OkHttpClient.class);
        when(client.newCall(any())).thenReturn(call);

        server = new MockWebServer();
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void alwaysCloseResponseOnSuccessfulRequest() {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponseBody(okhttp3.Response response) {
                    return "";
                }
            }.execute().getBody();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(nullValue()));
        verify(response, times(1)).close();
    }

    @Test
    public void alwaysCloseResponseOnRateLimitException() {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponseBody(okhttp3.Response response) throws Auth0Exception {
                    throw new RateLimitException(-1, -1, -1);
                }
            }.execute().getBody();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(RateLimitException.class)));
        assertThat(exception.getMessage(), is("Request failed with status code 429: Rate limit reached"));
        verify(response, times(1)).close();
    }

    @Test
    public void alwaysCloseResponseOnAPIException() {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponseBody(okhttp3.Response response) throws Auth0Exception {
                    throw new APIException("APIException", 500, null);
                }
            }.execute().getBody();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(APIException.class)));
        assertThat(exception.getMessage(), is("Request failed with status code 500: APIException"));
        verify(response, times(1)).close();
    }

    @Test
    public void alwaysCloseResponseOnAuth0Exception() {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponseBody(okhttp3.Response response) throws Auth0Exception {
                    throw new Auth0Exception("Auth0Exception");
                }
            }.execute().getBody();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(Auth0Exception.class)));
        assertThat(exception.getMessage(), is("Auth0Exception"));
        verify(response, times(1)).close();
    }

    @Test
    public void asyncCompletesWithExceptionWhenRequestCreationFails() throws Exception {
        CompletableFuture<com.auth0.net.Response<String>> request = new MockBaseRequest<String>(client) {
            @Override
            protected String parseResponseBody(okhttp3.Response response) throws Auth0Exception {
                throw new Auth0Exception("Response Parsing Error");
            }
            @Override
            protected okhttp3.Request createRequest() throws Auth0Exception {
                throw new Auth0Exception("Create Request Error");
            }
        }.executeAsync();

        Exception exception = null;
        Object result = null;

        try {
            result = request.get();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(result, is(nullValue()));
        assertThat(exception, is(notNullValue()));
        assertThat(exception.getCause(), is(instanceOf(Auth0Exception.class)));
        assertThat(exception.getCause().getMessage(), is("Create Request Error"));
    }

    @Test
    public void asyncCompletesWithExceptionWhenRequestFails() throws Exception {
        doReturn(call).when(client).newCall(any());

        doAnswer(invocation -> {
            ((Callback) invocation.getArgument(0)).onFailure(call, new IOException("Error!"));
            return null;
        }).when(call).enqueue(any());

        CompletableFuture<?> request = new MockBaseRequest<String>(client) {
            @Override
            protected String parseResponseBody(okhttp3.Response response) throws Auth0Exception {
                throw new Auth0Exception("Response Parsing Error");
            }
        }.executeAsync();

        Exception exception = null;
        Object result = null;

        try {
            result = request.get();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(result, is(nullValue()));
        assertThat(exception.getCause(), is(instanceOf(Auth0Exception.class)));
        assertThat(exception.getCause().getMessage(), is("Failed to execute request"));
        assertThat(exception.getCause().getCause(), is(instanceOf(IOException.class)));
        assertThat(exception.getCause().getCause().getMessage(), is("Error!"));
    }

    @Test
    public void asyncCompletesWithExceptionWhenResponseParsingFails() throws Exception {
        doReturn(call).when(client).newCall(any());

        doAnswer(invocation -> {
            ((Callback) invocation.getArgument(0)).onResponse(call, response);
            return null;
        }).when(call).enqueue(any());

        CompletableFuture<?> request = new MockBaseRequest<String>(client) {
            @Override
            protected String parseResponseBody(okhttp3.Response response) throws Auth0Exception {
                throw new Auth0Exception("Response Parsing Error");
            }
        }.executeAsync();

        Exception exception = null;
        Object result = null;

        try {
            result = request.get();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(result, is(nullValue()));
        assertThat(exception, is(notNullValue()));
        assertThat(exception.getCause(), is(instanceOf(Auth0Exception.class)));
        assertThat(exception.getCause().getMessage(), is("Response Parsing Error"));
    }

    @Test
    public void asyncCompletesSuccessfully() {
        doReturn(call).when(client).newCall(any());

        doAnswer(invocation -> {
            ((Callback) invocation.getArgument(0)).onResponse(call, response);
            return null;
        }).when(call).enqueue(any());

        CompletableFuture<com.auth0.net.Response<String>> request = new MockBaseRequest<String>(client) {
            @Override
            protected String parseResponseBody(okhttp3.Response response) throws Auth0Exception {
                return "Success";
            }
        }.executeAsync();

        Exception exception = null;
        Response<String> result = null;

        try {
            result = request.get();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(nullValue()));
        assertThat(result, is(instanceOf(Response.class)));
        assertThat(result.getBody(), is("Success"));
    }

    @Test
    public void returnsResponseInfo() throws Exception {
        Headers headers = new Headers.Builder().add("Content-Type", "text/plain").build();
        when(response.code()).thenReturn(200);
        when(response.headers()).thenReturn(headers);

        Response<String> a0response = new MockBaseRequest<String>(client) {
            @Override
            protected String parseResponseBody(okhttp3.Response response) {
                return "success";
            }
        }.execute();

        assertThat(a0response.getStatusCode(), is(200));
        assertThat(a0response.getHeaders().size(), is(1));
        assertThat(a0response.getHeaders().get("Content-Type"), is("text/plain"));
        assertThat(a0response.getBody(), is("success"));
    }

    @Test
    public void returnsResponseInfoWhenAsync() throws Exception {
        doAnswer(invocation -> {
            ((Callback) invocation.getArgument(0)).onResponse(call, response);
            return null;
        }).when(call).enqueue(any());

        Headers headers = new Headers.Builder().add("Content-Type", "text/plain").build();
        when(response.code()).thenReturn(200);
        when(response.headers()).thenReturn(headers);

        Response<String> a0response = new MockBaseRequest<String>(client) {
            @Override
            protected String parseResponseBody(okhttp3.Response response) {
                return "success";
            }
        }.executeAsync().get();

        assertThat(a0response.getStatusCode(), is(200));
        assertThat(a0response.getHeaders().size(), is(1));
        assertThat(a0response.getHeaders().get("Content-Type"), is("text/plain"));
        assertThat(a0response.getBody(), is("success"));
    }


    private abstract static class MockBaseRequest<String> extends BaseRequest<String> {
        MockBaseRequest(OkHttpClient client) {
            super(client);
        }

        @Override
        protected Request createRequest() throws Auth0Exception {
            return null;
        }
    }

}
