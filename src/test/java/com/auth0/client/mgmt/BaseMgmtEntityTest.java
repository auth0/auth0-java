package com.auth0.client.mgmt;

import com.auth0.client.MockServer;
import org.junit.After;
import org.junit.Before;
import org.junit.rules.ExpectedException;

public class BaseMgmtEntityTest {

    private static final String API_TOKEN = "apiToken";

    protected MockServer server;
    protected MgmtAPI api;
    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        api = new MgmtAPI(server.getBaseUrl(), API_TOKEN);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }
}
