package com.auth0.core;

import com.auth0.android.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, manifest = Config.NONE)
public class StrategiesTest {
    @Test
    public void shouldReturnUnknownSocial() {
        Strategies unknownSocial = Strategies.fromName("this-strategy-does-not-exist");
        assertEquals(unknownSocial.getType(), Strategies.Type.SOCIAL);
    }

    @Test
    public void shouldReturnValidDatabaseStrategy() {
        Strategies auth0Database = Strategies.fromName("auth0");
        assertEquals(auth0Database.getType(), Strategies.Type.DATABASE);
    }
}
