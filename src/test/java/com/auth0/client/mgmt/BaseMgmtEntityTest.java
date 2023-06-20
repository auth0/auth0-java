package com.auth0.client.mgmt;

import com.auth0.client.MockServer;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;

public class BaseMgmtEntityTest {

    private static final String API_TOKEN = "apiToken";

    protected MockServer server;
    protected ManagementAPI api;

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeEach
    public void setUp() throws Exception {
        server = new MockServer();
        api = ManagementAPI.newBuilder(server.getBaseUrl(), API_TOKEN).build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        server.stop();
    }
}
