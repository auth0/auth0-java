package com.auth0;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MockServer {

    public static final String AUTH_USER_INFO = "src/test/resources/auth/user_info.json";
    public static final String AUTH_RESET_PASSWORD = "src/test/resources/auth/reset_password.json";
    public static final String AUTH_SIGN_UP = "src/test/resources/auth/sign_up.json";
    public static final String AUTH_TOKENS = "src/test/resources/auth/tokens.json";
    public static final String AUTH_ERROR_WITH_ERROR_DESCRIPTION = "src/test/resources/auth/error_with_error_description.json";
    public static final String AUTH_ERROR_WITH_ERROR = "src/test/resources/auth/error_with_error.json";
    public static final String AUTH_ERROR_WITH_DESCRIPTION = "src/test/resources/auth/error_with_description.json";
    public static final String AUTH_ERROR_PLAINTEXT = "src/test/resources/auth/error_plaintext.json";
    public static final String MGMT_CLIENT_GRANTS_LIST = "src/test/resources/mgmt/client_grants_list.json";
    public static final String MGMT_CLIENT_GRANT = "src/test/resources/mgmt/client_grant.json";
    public static final String MGMT_EMPTY_LIST = "src/test/resources/mgmt/empty_list.json";


    private final MockWebServer server;

    public MockServer() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    public void stop() throws IOException {
        server.shutdown();
    }

    public String getBaseUrl() {
        return server.url("/").toString();
    }

    public RecordedRequest takeRequest() throws InterruptedException {
        return server.takeRequest();
    }

    private String readTextFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public void jsonResponse(String path, int statusCode) throws IOException {
        MockResponse response = new MockResponse()
                .setResponseCode(statusCode)
                .addHeader("Content-Type", "application/json")
                .setBody(readTextFile(path));
        server.enqueue(response);
    }

    public void textResponse(String path, int statusCode) throws IOException {
        MockResponse response = new MockResponse()
                .setResponseCode(statusCode)
                .addHeader("Content-Type", "text/plain")
                .setBody(readTextFile(path));
        server.enqueue(response);
    }

    public static Map<String, Object> bodyFromRequest(RecordedRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
        Buffer body = request.getBody();
        try {
            return mapper.readValue(body.inputStream(), mapType);
        } catch (IOException e) {
            throw e;
        } finally {
            body.close();
        }
    }
}
