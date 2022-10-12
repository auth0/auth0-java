package com.auth0.net;

import com.auth0.client.MockServer;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RateLimitException;
import com.auth0.json.mgmt.users.User;
import com.auth0.json.mgmt.users.UsersPage;
import okhttp3.Request;
import okhttp3.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
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

    // TODO test that our Response populates headers/body/code from OkHttp Response
    @Test
    public void setsHeadersAndCode() throws Exception {
        Map<String, Object> headers = Collections.singletonMap("name", "val");
        String body = "body";
//        okhttp3.Response okResponse = mock(okhttp3.Response.class);


        MockResponse okResponse = new MockResponse()
            .setResponseCode(200)
            .setBody(body);
        headers.forEach(okResponse::addHeader);

        // TODO the mockResponse isn't being sent back. Why?
        server.enqueue(okResponse);

        Exception exception = null;
        Response<String> response = null;
        try {
            response = new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponse(okhttp3.Response response) {
                    return body;
                }
            }.execute();
        } catch (Exception e) {
            exception = e;
        }

//        server.takeRequest();

        assertThat(exception, is(nullValue()));
        assertThat(response, is(notNullValue()));
        assertThat(response.getBody(), is(body));
        assertThat(response.getStatusCode(), is(200));
//
//        MockServer server = new MockServer();
//        ManagementAPI api = new ManagementAPI(server.getBaseUrl(), "token");
//        server.textResponse("body", 200, Collections.singletonMap("name", "val"));
//        Response<User> response = api.users().get("user-id", null).execute();
//        assertThat(response.getStatusCode(), is(200));
//        assertThat(response.getHeaders(), is(Collections.singletonMap("name", "val")));
    }

    @Test
    public void deleteMe() throws Exception {
        ManagementAPI api = new ManagementAPI("jim-anderson.auth0.com", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InhYaGphZjllQVpWY3JseVZGaVlWUyJ9.eyJpc3MiOiJodHRwczovL2ppbS1hbmRlcnNvbi5hdXRoMC5jb20vIiwic3ViIjoiVWFTUXphb3Z1QkZzMVZ0SmZPYm9naXExODFSdFJkRkpAY2xpZW50cyIsImF1ZCI6Imh0dHBzOi8vamltLWFuZGVyc29uLmF1dGgwLmNvbS9hcGkvdjIvIiwiaWF0IjoxNjY1NTQ3NjQyLCJleHAiOjE2NjU2MzQwNDIsImF6cCI6IlVhU1F6YW92dUJGczFWdEpmT2JvZ2lxMTgxUnRSZEZKIiwic2NvcGUiOiJyZWFkOmNsaWVudF9ncmFudHMgY3JlYXRlOmNsaWVudF9ncmFudHMgZGVsZXRlOmNsaWVudF9ncmFudHMgdXBkYXRlOmNsaWVudF9ncmFudHMgcmVhZDp1c2VycyB1cGRhdGU6dXNlcnMgZGVsZXRlOnVzZXJzIGNyZWF0ZTp1c2VycyByZWFkOnVzZXJzX2FwcF9tZXRhZGF0YSB1cGRhdGU6dXNlcnNfYXBwX21ldGFkYXRhIGRlbGV0ZTp1c2Vyc19hcHBfbWV0YWRhdGEgY3JlYXRlOnVzZXJzX2FwcF9tZXRhZGF0YSByZWFkOnVzZXJfY3VzdG9tX2Jsb2NrcyBjcmVhdGU6dXNlcl9jdXN0b21fYmxvY2tzIGRlbGV0ZTp1c2VyX2N1c3RvbV9ibG9ja3MgY3JlYXRlOnVzZXJfdGlja2V0cyByZWFkOmNsaWVudHMgdXBkYXRlOmNsaWVudHMgZGVsZXRlOmNsaWVudHMgY3JlYXRlOmNsaWVudHMgcmVhZDpjbGllbnRfa2V5cyB1cGRhdGU6Y2xpZW50X2tleXMgZGVsZXRlOmNsaWVudF9rZXlzIGNyZWF0ZTpjbGllbnRfa2V5cyByZWFkOmNvbm5lY3Rpb25zIHVwZGF0ZTpjb25uZWN0aW9ucyBkZWxldGU6Y29ubmVjdGlvbnMgY3JlYXRlOmNvbm5lY3Rpb25zIHJlYWQ6cmVzb3VyY2Vfc2VydmVycyB1cGRhdGU6cmVzb3VyY2Vfc2VydmVycyBkZWxldGU6cmVzb3VyY2Vfc2VydmVycyBjcmVhdGU6cmVzb3VyY2Vfc2VydmVycyByZWFkOmRldmljZV9jcmVkZW50aWFscyB1cGRhdGU6ZGV2aWNlX2NyZWRlbnRpYWxzIGRlbGV0ZTpkZXZpY2VfY3JlZGVudGlhbHMgY3JlYXRlOmRldmljZV9jcmVkZW50aWFscyByZWFkOnJ1bGVzIHVwZGF0ZTpydWxlcyBkZWxldGU6cnVsZXMgY3JlYXRlOnJ1bGVzIHJlYWQ6cnVsZXNfY29uZmlncyB1cGRhdGU6cnVsZXNfY29uZmlncyBkZWxldGU6cnVsZXNfY29uZmlncyByZWFkOmhvb2tzIHVwZGF0ZTpob29rcyBkZWxldGU6aG9va3MgY3JlYXRlOmhvb2tzIHJlYWQ6YWN0aW9ucyB1cGRhdGU6YWN0aW9ucyBkZWxldGU6YWN0aW9ucyBjcmVhdGU6YWN0aW9ucyByZWFkOmVtYWlsX3Byb3ZpZGVyIHVwZGF0ZTplbWFpbF9wcm92aWRlciBkZWxldGU6ZW1haWxfcHJvdmlkZXIgY3JlYXRlOmVtYWlsX3Byb3ZpZGVyIGJsYWNrbGlzdDp0b2tlbnMgcmVhZDpzdGF0cyByZWFkOmluc2lnaHRzIHJlYWQ6dGVuYW50X3NldHRpbmdzIHVwZGF0ZTp0ZW5hbnRfc2V0dGluZ3MgcmVhZDpsb2dzIHJlYWQ6bG9nc191c2VycyByZWFkOnNoaWVsZHMgY3JlYXRlOnNoaWVsZHMgdXBkYXRlOnNoaWVsZHMgZGVsZXRlOnNoaWVsZHMgcmVhZDphbm9tYWx5X2Jsb2NrcyBkZWxldGU6YW5vbWFseV9ibG9ja3MgdXBkYXRlOnRyaWdnZXJzIHJlYWQ6dHJpZ2dlcnMgcmVhZDpncmFudHMgZGVsZXRlOmdyYW50cyByZWFkOmd1YXJkaWFuX2ZhY3RvcnMgdXBkYXRlOmd1YXJkaWFuX2ZhY3RvcnMgcmVhZDpndWFyZGlhbl9lbnJvbGxtZW50cyBkZWxldGU6Z3VhcmRpYW5fZW5yb2xsbWVudHMgY3JlYXRlOmd1YXJkaWFuX2Vucm9sbG1lbnRfdGlja2V0cyByZWFkOnVzZXJfaWRwX3Rva2VucyBjcmVhdGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiBkZWxldGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiByZWFkOmN1c3RvbV9kb21haW5zIGRlbGV0ZTpjdXN0b21fZG9tYWlucyBjcmVhdGU6Y3VzdG9tX2RvbWFpbnMgdXBkYXRlOmN1c3RvbV9kb21haW5zIHJlYWQ6ZW1haWxfdGVtcGxhdGVzIGNyZWF0ZTplbWFpbF90ZW1wbGF0ZXMgdXBkYXRlOmVtYWlsX3RlbXBsYXRlcyByZWFkOm1mYV9wb2xpY2llcyB1cGRhdGU6bWZhX3BvbGljaWVzIHJlYWQ6cm9sZXMgY3JlYXRlOnJvbGVzIGRlbGV0ZTpyb2xlcyB1cGRhdGU6cm9sZXMgcmVhZDpwcm9tcHRzIHVwZGF0ZTpwcm9tcHRzIHJlYWQ6YnJhbmRpbmcgdXBkYXRlOmJyYW5kaW5nIGRlbGV0ZTpicmFuZGluZyByZWFkOmxvZ19zdHJlYW1zIGNyZWF0ZTpsb2dfc3RyZWFtcyBkZWxldGU6bG9nX3N0cmVhbXMgdXBkYXRlOmxvZ19zdHJlYW1zIGNyZWF0ZTpzaWduaW5nX2tleXMgcmVhZDpzaWduaW5nX2tleXMgdXBkYXRlOnNpZ25pbmdfa2V5cyByZWFkOmxpbWl0cyB1cGRhdGU6bGltaXRzIGNyZWF0ZTpyb2xlX21lbWJlcnMgcmVhZDpyb2xlX21lbWJlcnMgZGVsZXRlOnJvbGVfbWVtYmVycyByZWFkOmVudGl0bGVtZW50cyByZWFkOmF0dGFja19wcm90ZWN0aW9uIHVwZGF0ZTphdHRhY2tfcHJvdGVjdGlvbiByZWFkOm9yZ2FuaXphdGlvbnMgdXBkYXRlOm9yZ2FuaXphdGlvbnMgY3JlYXRlOm9yZ2FuaXphdGlvbnMgZGVsZXRlOm9yZ2FuaXphdGlvbnMgY3JlYXRlOm9yZ2FuaXphdGlvbl9tZW1iZXJzIHJlYWQ6b3JnYW5pemF0aW9uX21lbWJlcnMgZGVsZXRlOm9yZ2FuaXphdGlvbl9tZW1iZXJzIGNyZWF0ZTpvcmdhbml6YXRpb25fY29ubmVjdGlvbnMgcmVhZDpvcmdhbml6YXRpb25fY29ubmVjdGlvbnMgdXBkYXRlOm9yZ2FuaXphdGlvbl9jb25uZWN0aW9ucyBkZWxldGU6b3JnYW5pemF0aW9uX2Nvbm5lY3Rpb25zIGNyZWF0ZTpvcmdhbml6YXRpb25fbWVtYmVyX3JvbGVzIHJlYWQ6b3JnYW5pemF0aW9uX21lbWJlcl9yb2xlcyBkZWxldGU6b3JnYW5pemF0aW9uX21lbWJlcl9yb2xlcyBjcmVhdGU6b3JnYW5pemF0aW9uX2ludml0YXRpb25zIHJlYWQ6b3JnYW5pemF0aW9uX2ludml0YXRpb25zIGRlbGV0ZTpvcmdhbml6YXRpb25faW52aXRhdGlvbnMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.s0dXFEbO2HuTFJC5pq8KyCeXoso7QmdUzVbZQ5wnZKiI9XmqSxSPVeX9Y1Hlj-UN2TvHOKrTdeq0B-B9IVqeQSz64qkWhLctHrx6Ny9KCq3xrlU2vzxH6lf9kYJup0GtJhd14J45Uybe5GG7EwravBFbl3Fz9w7AfLPOPREqlhKYqSwh4fh31BRiKlYV2xcASxXbVc3bohdbxlEbU_6QyerNPrbTGREwwbAM_wRLN36DdW6T6B9stjzm2ICDUYF4Pf-mLNRzokGNchUIg8Kse8dnhAcbn2ArbTAD-98NdbrllRWs6TvJBuIv5EZJE6JdmzuRGdzncEroR92qLN_5RQ");
        Response<UsersPage> usersPage = api.users().list(null).execute();
        System.out.println(usersPage.getBody());
        System.out.println(usersPage.getStatusCode());
        System.out.println(usersPage.getHeaders());
    }

    @Test
    public void alwaysCloseResponseOnSuccessfulRequest() {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponse(okhttp3.Response response) {
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
                protected String parseResponse(okhttp3.Response response) throws Auth0Exception {
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
                protected String parseResponse(okhttp3.Response response) throws Auth0Exception {
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
                protected String parseResponse(okhttp3.Response response) throws Auth0Exception {
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
            protected String parseResponse(okhttp3.Response response) throws Auth0Exception {
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
            protected String parseResponse(okhttp3.Response response) throws Auth0Exception {
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
            protected String parseResponse(okhttp3.Response response) throws Auth0Exception {
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
            protected String parseResponse(okhttp3.Response response) throws Auth0Exception {
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
