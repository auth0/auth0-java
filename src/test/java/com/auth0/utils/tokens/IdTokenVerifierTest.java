package com.auth0.utils.tokens;

import com.auth0.AssertsUtil;
import com.auth0.exception.IdTokenValidationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.AssertsUtil.verifyThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IdTokenVerifierTest {

    private final static String DOMAIN = "tokens-test.auth0.com";
    private final static String AUDIENCE = "tokens-test-123";

    // Default clock time of September 2, 2019 5:00:00 AM GMT
    private final static Date DEFAULT_CLOCK = new Date(1567400400000L);
    private final static Integer DEFAULT_CLOCK_SKEW = 60;

    private SignatureVerifier signatureVerifier;

    @BeforeEach
    public void setUp() {
        signatureVerifier = mock(SignatureVerifier.class);
    }

    @Test
    public void failsToCreateOptionsWhenIssuerIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> IdTokenVerifier.init(null, "audience", signatureVerifier));
    }

    @Test
    public void failsToCreateOptionsWhenAudienceIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> IdTokenVerifier.init("issuer", null, signatureVerifier));
    }

    @Test
    public void failsToCreateOptionsWhenVerifierIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> IdTokenVerifier.init("issuer", "audience", null));
    }

    @Test
    public void failsWhenIDTokenMissing() {
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> IdTokenVerifier.init("issuer", "audience", signatureVerifier)
                .build()
                .verify(null),
            "ID token is required but missing");
    }

    @Test
    public void failsWhenIDTokenEmpty() {
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> IdTokenVerifier.init("issuer", "audience", signatureVerifier)
                .build()
                .verify(""),
            "ID token is required but missing");
    }

    @Test
    public void failsWhenTokenCannotBeDecoded() {
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> IdTokenVerifier.init(DOMAIN, AUDIENCE, SignatureVerifier.forHS256("secret"))
                .build()
                .verify("boom"),
            "ID token could not be decoded");
    }

    @Test
    public void failsWhenSignatureIsInvalid() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub25jZSI6IjEyMzQiLCJpc3MiOiJodHRwczovL21lLmF1dGgwLmNvbS8iLCJhdWQiOiJkYU9nbkdzUlloa3d1NjIxdmYiLCJzdWIiOiJhdXRoMHx1c2VyMTIzIn0.a7ayNmFTxS2D-EIoUikoJ6dck7I8veWyxnje_mYD3qY";

        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> IdTokenVerifier.init(DOMAIN, AUDIENCE, SignatureVerifier.forHS256("asdlk59ckvkr"))
                .build()
                .verify(token),
            "Invalid ID token signature");
    }

    @Test
    public void failsWhenIssuerMissing() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMiLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.B4PGlucyy-fJ4v5NNK2hntvjAf5m8dJf84WttwVnzV0ZlfPbYUSJm7Vc1ys7iMqXAQzAl2I8bDf2qhtLjaLpDKAH9JUvowUpCL7Bgjd7AEc1Te_IUwwxlpCupgseOEL2nrY8enP6On7BO7BBpngmVwnD1DvuA4lNoaaFyWUopha5Dxd5jw64wMqP4lz13C6Kqs8mINZkkw-NgE8DvWszaXeyPaowy-QpfXmPBnw75YLZlGcjr-WQsWQV7rUezq4Tl_11uPivR-fNcEWdG1mAtsnQnB_zJJKaHYlE0g4fey_6H9FKmCvcNkpBGo9ylbitb7jIuExbFEvEd2r_4wKl0g";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                .build()
                .verify(token),
            "Issuer (iss) claim must be a string present in the ID token");
    }

    @Test
    public void failsWhenIssuerInvalid() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzb21ldGhpbmctZWxzZSIsInN1YiI6ImF1dGgwfDEyMzQ1Njc4OSIsImF1ZCI6WyJ0b2tlbnMtdGVzdC0xMjMiLCJleHRlcm5hbC10ZXN0LTEyMyJdLCJleHAiOjE1Njc0ODY4MDAsImlhdCI6MTU2NzMxNDAwMCwibm9uY2UiOiJhNTl2azU5MiIsImF6cCI6InRva2Vucy10ZXN0LTEyMyIsImF1dGhfdGltZSI6MTU2NzMxNDAwMH0.lHFHyg1ei3hK2vB7X1xB9nqksAEnxtv2KKpE_Gih6RezTruF9uZu1PAZTEwxhfj2UrQxwLqCb-t6wyVnxVpCsymSCq9JIiCVgg_cYV38siMs38N9y26BrVeyifj_VOP9Om_vI_hHjOzhi8WmysK2KKAQnn0skKAkq8epY4axCX3NkRaEIMhhTaITYia3GbJ5Qki8WDD9UVucUVOhgSZBV5p1dL39FKgc9k1MOVZJG-zAd_r5GsUIRk-xUwNX0WYwCR9sC2G-FjJTvlFph_4vksponoUWJ-LPTLM0RwGgmEUPhhnIG23UjsNwpnElY4gWfIL0hsO98-5DpGjn8Ejr0w";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                .build()
                .verify(token),
            String.format("Issuer (iss) claim mismatch in the ID token, expected \"%s\", found \"%s\"",
                "https://" + DOMAIN + "/", "something-else"));
    }

    @Test
    public void failsWhenSubMissing() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMiLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.fDR9NSbbt75w9nzhL-eBfGjOp16HP2vfnO6m_Oav0xrmmgyYsBZSLOPd2C0O46bp6_2hKjeOUhnwYwjocsdXI4hvfQkyACERtneCkwHwSZPZK-1h6vgGF7b_7ILUywEcgo7F6e1qgFTM93Prqk63cCP53KgOBPyx02y0rqkhUOApCWRVBFrfP92tXvFN7E2phmpf9G68PPjwnEvvQtYOGjvFkaWSja7MKT98f7OxgbenBI_mAZy9LmOqUl3SKJOBe5Fibs1snI0l4nzrgQ1GNxVwyfHOdyq-srdGe8rlFx5kdhWh313EOzWxxGTg4RhGY7Tiz1QWago0VQ5yOt0w8A";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                .build()
                .verify(token),
            "Subject (sub) claim must be a string present in the ID token");
    }

    @Test
    public void failsWhenAudienceMissing() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJleHAiOjE1Njc0ODY4MDAsImlhdCI6MTU2NzMxNDAwMCwibm9uY2UiOiJhNTl2azU5MiIsImF6cCI6InRva2Vucy10ZXN0LTEyMyIsImF1dGhfdGltZSI6MTU2NzMxNDAwMH0.XM-IM9CIZ2cJpZZaKooMSmNgvwHPTse6kcIOPATgewRZxrDdCEjtPHmzmSuyDGy84vSR__DJS_kM2jWWwbkjB_PahXes210dpUqitRW3is9xV0-k0LkVwxmhHCM-e9sClbTbcs4zLv6WWFRq4UEU5DU6HhuHLQeeH0eO2Nv_tkvu-JdpmoepHPjW3ecMs0lhzXRT6_2o-ErTPdYt4W6yqpBG57HRIMzs9F72AWcPC6vhLY0IhMqXaq68Ma3jnEPIXUmv52bll0PuQVBqKd-eDH_jD0ZHFUCkwbfWPrkhJz5Q5qLzSzUjnrWKA3KgP4_Z1KfHY2-nQA2ynMgNFSn_eA";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                .build()
                .verify(token),
            "Audience (aud) claim must be a string or array of strings present in the ID token");
    }

    @Test
    public void failsWhenAudienceDoesNotContainClientId() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOiJleHRlcm5hbC10ZXN0LTEyMyIsImV4cCI6MTU2NzQ4NjgwMCwiaWF0IjoxNTY3MzE0MDAwLCJub25jZSI6ImE1OXZrNTkyIiwiYXpwIjoidG9rZW5zLXRlc3QtMTIzIiwiYXV0aF90aW1lIjoxNTY3MzE0MDAwfQ.SxeNIhm8reywgtSSkZ6jCpbZ8KyC09couFjpcrJFktAYKmJZnGQkv0gQLNUuGejORvysznOlhfO2nkF10yT6pKBiye9xZ8TstWQBorDKHL-74n6ZAxjPg1F0vHNokZq0zpPkwV-gKIFY6aPw3vyZTxzR6CMyoJdwc19A0RXPzPt6T7csQeqX0lzGEqqeIbU4VI5XM5RG1VvN82CgTlOQXlFZrKhyJx_xwslyWWDzx7tpPNid1wusvfznTGxoWO2wUBCyW6EhmyHp2euFi1gdJqHQVbrydutPtQ-FGQEwyWACNN8kBWqQ7UEbqimg6C0NTGrRkkKkJ79DmiW7aULHZQ";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                .build()
                .verify(token),
            String.format("Audience (aud) claim mismatch in the ID token; expected \"%s\" but found \"%s\"", AUDIENCE, "[external-test-123]"));
    }

    @Test
    public void failsWhenExpClaimMissing() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiaWF0IjoxNTY3MzE0MDAwLCJub25jZSI6ImE1OXZrNTkyIiwiYXpwIjoidG9rZW5zLXRlc3QtMTIzIiwiYXV0aF90aW1lIjoxNTY3MzE0MDAwfQ.b6saYAZCnCSzpVO0nrAUKVSC1n3GoqUfwrjOXG5gVxda0oFohpYJe68QwzsTmS4fOm7JtbN1FqjVRN6S4i-BnH-XGnciGOMFF4EfaOzsgo7DCrrLrjfx6rmqW8UPYalbfJTQL8mXYnLOxzMGP3DEXNlk-41GSZoFujwTAIqYjrV_Y3MUGYmzcVxdL_h2psLm_p07knMLCm7Cuo8znzKrU4PtuaLflvzorg57S4BD79oLv4uv0_dmhwPUgJDvqWeicR5Qry4aX2L5BT6V-nBWAcu3qVZDymSKcjtTebxszxY1siyA7BQe88ZmgP1bW1KXtMk_fOGsgWHFdu_AH77yow";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                .build()
                .verify(token),
            "Expiration Time (exp) claim must be a number present in the ID token");
    }

    @Test
    public void failsWhenExpClaimInvalidOutsideDefaultLeeway() {
        Integer actualExpTime = 1567314000;

        // set clock to September 1, 2019 5:00:00 AM GMT
        Date clock = new Date(1567314000000L);
        clock.setTime(clock.getTime() + ((DEFAULT_CLOCK_SKEW + 1) * 1000));

        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3MzE0MDAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMiLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.uDn-4wtiigGddUw2kis_QyfDE3w75rWvu9NolMgD3b7l4_fedhQOk-z_mYID588ZXpnpLRKKiD5I2IFsXl7Qcc10rx1LIZxNqdzyc3VrgFf677x7fFZ4guR2WalH-zdJEluruMRdCIFQczIjXnGKPHGQ8gPH1LRozv43dl-bO2viX6MU4pTgNq3GIsU4ureyHrx1o9JSqF4b_RzuYvVWVVX7ABC2csMJP_ocVbEIQjUBhp1V7VcQY-Zgq0prk_HvY13g8FxK4KvSza637ZWAfonn599SKuy22PeMJqDfd64SbunWrt-mKBz9PHeAo9t4LJPLsAqSd3IQ2aJTsnqJRA";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                    .withClock(clock)
                    .build()
                    .verify(token),
            String.format("Expiration Time (exp) claim error in the ID token; current time (%d) is after expiration time (%d)",
                clock.getTime() / 1000, actualExpTime + DEFAULT_CLOCK_SKEW));
    }

    @Test
    public void succeedsWhenExpClaimInPastButWithinDefaultLeeway() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3MzE0MDAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMiLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.uDn-4wtiigGddUw2kis_QyfDE3w75rWvu9NolMgD3b7l4_fedhQOk-z_mYID588ZXpnpLRKKiD5I2IFsXl7Qcc10rx1LIZxNqdzyc3VrgFf677x7fFZ4guR2WalH-zdJEluruMRdCIFQczIjXnGKPHGQ8gPH1LRozv43dl-bO2viX6MU4pTgNq3GIsU4ureyHrx1o9JSqF4b_RzuYvVWVVX7ABC2csMJP_ocVbEIQjUBhp1V7VcQY-Zgq0prk_HvY13g8FxK4KvSza637ZWAfonn599SKuy22PeMJqDfd64SbunWrt-mKBz9PHeAo9t4LJPLsAqSd3IQ2aJTsnqJRA";

        // set clock to September 1, 2019 5:00:00 AM GMT
        Date clock = new Date(1567314000000L);
        clock.setTime(clock.getTime() + ((DEFAULT_CLOCK_SKEW - 1) * 1000));

        configureVerifier(token)
                .withClock(clock)
                .build()
                .verify(token);
    }

    @Test
    public void failsWhenExpClaimInvalidOutsideCustomLeeway() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3MzE0MDAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMiLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.uDn-4wtiigGddUw2kis_QyfDE3w75rWvu9NolMgD3b7l4_fedhQOk-z_mYID588ZXpnpLRKKiD5I2IFsXl7Qcc10rx1LIZxNqdzyc3VrgFf677x7fFZ4guR2WalH-zdJEluruMRdCIFQczIjXnGKPHGQ8gPH1LRozv43dl-bO2viX6MU4pTgNq3GIsU4ureyHrx1o9JSqF4b_RzuYvVWVVX7ABC2csMJP_ocVbEIQjUBhp1V7VcQY-Zgq0prk_HvY13g8FxK4KvSza637ZWAfonn599SKuy22PeMJqDfd64SbunWrt-mKBz9PHeAo9t4LJPLsAqSd3IQ2aJTsnqJRA";
        int leeway = 120;

        Date actualExp = JWT.decode(token).getExpiresAt();

        // set clock to September 1, 2019 5:00:00 AM GMT
        Date clock = new Date(1567314000000L);
        clock.setTime(clock.getTime() + ((leeway + 1) * 1000));

        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                    .withClock(clock)
                    .withLeeway(leeway)
                    .build()
                    .verify(token),
            String.format("Expiration Time (exp) claim error in the ID token; current time (%d) is after expiration time (%d)",
                clock.getTime() / 1000, ((actualExp.getTime() / 1000) + leeway)));
    }

    @Test
    public void succeedsWhenExpClaimInPastButWithinCustomLeeway() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3MzE0MDAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMiLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.uDn-4wtiigGddUw2kis_QyfDE3w75rWvu9NolMgD3b7l4_fedhQOk-z_mYID588ZXpnpLRKKiD5I2IFsXl7Qcc10rx1LIZxNqdzyc3VrgFf677x7fFZ4guR2WalH-zdJEluruMRdCIFQczIjXnGKPHGQ8gPH1LRozv43dl-bO2viX6MU4pTgNq3GIsU4ureyHrx1o9JSqF4b_RzuYvVWVVX7ABC2csMJP_ocVbEIQjUBhp1V7VcQY-Zgq0prk_HvY13g8FxK4KvSza637ZWAfonn599SKuy22PeMJqDfd64SbunWrt-mKBz9PHeAo9t4LJPLsAqSd3IQ2aJTsnqJRA";
        Integer leeway = 120;

        // set clock to September 1, 2019 5:00:00 AM GMTExpiration Time (exp) claim error in the ID token; current time
        Date clock = new Date(1567314000000L);
        clock.setTime(clock.getTime() + ((leeway - 1) * 1000));

        configureVerifier(token)
                .withLeeway(leeway)
                .withClock(clock)
                .build()
                .verify(token);
    }

    @Test
    public void failsWhenIatClaimMissing() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJub25jZSI6ImE1OXZrNTkyIiwiYXpwIjoidG9rZW5zLXRlc3QtMTIzIiwiYXV0aF90aW1lIjoxNTY3MzE0MDAwfQ.SJDgK8W9Y8stMtE9LG2OzHzXzbIDCXg8lRhKyOim4rRXbkg3k0on7gCzN-sy2d5z5TQ-lQzbY3V4z-so3ltVDUYd_8RjmUiKgNK_95UsxfTDM2BlBEQ6USMVl3ojC5jcTBhg5MF16ZBEn94IjIGC9Uks9GPseM-JrtUPx4Uj5VvsBtmeKxLc3rSGt7rYC4JU65Oa-O5pFYRSCbNzRFNHRlmnb5b2uPHxoVLjrJAT0FhlXcsNgfz65MlbXBgAyz7xjCEhw_tTpvptaCwPTeG0mgBYlGQ7Sl3xHJzgG4jLbA7Pvvfcx0MpBPHUZxADh1FFQnf2nHB0ppddiDfOq2mHNA";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                    .build()
                    .verify(token),
            "Issued At (iat) claim must be a number present in the ID token");
    }

    @Test
    public void failsWhenNonceConfiguredButNoNonceClaimSent() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsImF6cCI6InRva2Vucy10ZXN0LTEyMyIsImF1dGhfdGltZSI6MTU2NzMxNDAwMH0.ZRYK4s72pKXJUSadByPp_MNyuaACmPCyj9RaIfxuTTLXE45YJ0toLK6XjjDv_861E_fRmEKMthnJAmHcKXiDWGb73l3iDtD7clockBOo3KJO2cwkM1uYNpG1kbNkg6WDvgGlVsC7buxr8dbL8fI2e0g53Jl48lE9Ohi5Z_7iRmRoVAx5HE60UDfEqFeAKZyu5VsAahp9q3PwhLfaJVDobtAzWP0LcRA3x8FOA0ZdBBNpvRmeBRugU2GQTSDLSMtGzgi5xXUwXly7pr5bX-lIYICU1Q9R5n-8uYlEaFuiaYTqzxY0fmSzzGeFkwrj7b0yTQ2OwAFVT3MWCSbvjKsy-JWQ";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                .build()
                .verify(token, "nonce"),
            "Nonce (nonce) claim must be a string present in the ID token");
    }

    @Test
    public void failsWhenNonceIsInvalid() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiMDAwOTk5IiwiYXpwIjoidG9rZW5zLXRlc3QtMTIzIiwiYXV0aF90aW1lIjoxNTY3MzE0MDAwfQ.n4jIX01mNucMs92F8IZtKJeCvgUYPwrrOsaZX91fnzVkDC5tAqi4HLRGHjtUJe1PwmIijJk63FskeuApVPfxfAbITL1KBVDHiin2RVeDSAl5lhSnsSYW-k5MfzXx11MJxhS_VD5zvOgbWmuRYUHlc1zh48YyJZQE-OaEFvxGyyEM7Zhgzfz4D5_kjd2qV890WsXGs_GadyzxATfP59XENnPzMo3VLXyBC4cQ0e7rzBIqquBKo9-MT6rhy_qSwMrZJhyzSzE5gTtMd2Od9YgPUtLznBt34rBD1uJaSs_a4s1Ox3h4jTCm85xWFabGx3kz7xkD33nCiMKQ_FSy1d-toQ";

        String expectedNonce = "nonce";
        String actualNonce = "000999";

        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                .build()
                .verify(token, expectedNonce),
            String.format("Nonce (nonce) claim mismatch in the ID token; expected \"%s\", found \"%s\"", expectedNonce, actualNonce));
    }

    @Test
    public void failsWhenAudClaimHasMultipleItemsButAzpMissing() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOjQyLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.SliF71jOX9JsGeUPCySf3ucY_tGr3uh183cbcUN9ze3qRiOAc5bi7vdsBtODtlVJgsx0Elt0JrISTJ8SoNkpA4SxrjFpxSsfzPBwQtJrlg7pqflgBH7g6zKGVGRs2Z0jxZaCvXQvRuUZRZwFIncZ2zTFIDI3X5xLeJAGRGWaInOvLLlumGzWzfNLUG_G5uHZQW6sRgyIw9qrdqEWXO6sGjOBG9Au6jIo2IH0I53-UujAnNHWeJRPsM5xw2bHPteIde1xn4N0w26BlZ4GEQifVQDFw3ukah35SQ-ENMMS58Siu-sysF5F3oxdwVaMidyYgrD2VUN_iXIaMPwA2i0M5Q";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                .build()
                .verify(token),
            "Authorized Party (azp) claim must be a string present in the ID token when Audience (aud) claim has multiple values");
    }

    @Test
    public void failsWhenAudClaimHasMultipleItemsButAzpInvalid() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJleHRlcm5hbC10ZXN0LTEyMyIsImF1dGhfdGltZSI6MTU2NzMxNDAwMH0.GLuChuSum2S6h79rfRbJrJfe_7Fw_D6RHXj9zrAhixoNLMyBosO2GBPsOgoaLTDMonJzCyqskjan-w-SJ5nw7fUmDkWfPVjXcS0x5pt72j0dgfLMu6eOFIA9jWHWN4hsN3XKJktZ9202AohI8fXO5BYQ-jMi0HWQaiUj3f6wITHEN6fTydLo_t24hriExkO1670AgzM22BVTfb-JJlrs32t6ffY77zrF5ahIg_h4ROgrcf_3LejF7ZnubHbpJ-wX-byxW9YXT5tN_JjD5EP6jC37s9iL8ArGEZtBzHVfCO0kqlaH-9PVZXgz8SjMSJ8iA2fXXN0L35ySdzida3hhzw";
        String actualAzp = "external-test-123";

        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                .build()
                .verify(token),
            String.format("Authorized Party (azp) claim mismatch in the ID token; expected \"%s\", found \"%s\"", AUDIENCE, actualAzp));
    }

    @Test
    public void failsWhenMaxAgeSentButAuthTimeClaimMissing() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMifQ.Gb36qNHgQgac1fXh9AHX7ZMroymT0j4TjNol3ZirbIOyxuHV4OxCbGcoAAxC8Zt_dIc3DH9SX3QUIwTkE3DsFxS-VJ58R2d9RbXJl5p8pO1sJNFjo59njLKbiBxVil4z8PUsw77c_4f2QtKn6LHzhGqL9CS84LUCgNPPBsBHYyNRJDwIauPrrLyOsZAS3dWlZiUDBFurSYe0Y-O6d8zF_uKOcTD8A2E3SQQlZJQ12T94IprQ9V0tbbWI8VSGQ23JghR62QwZC-rBOF9pQMcLLCNRLFTTF9sXqZuS9XRv7PZ6rRjaonHDWn8WqGjSleWSycPsvwvjjSUVR8Z3iDBZig";
        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                    .build()
                    .verify(token, null, 200),
            "Authentication Time (auth_time) claim must be a number present in the ID token when Max Age (max_age) is specified");
    }

    @Test
    public void failsWhenMaxSentButAuthTimeInvalidWithinLeeway() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMiLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.AbSYZ_Tu0-ZelCRPuu9jOd9y1M19yIlk8bjSQDVVgAekRZLdRA_T_gi_JeWyFysKZVpRcHC1YJhTH4YH8CCMRTwviq3woIsLmdUecjydyZkHcUlhHXj2DbC15cyELalPNe3T9eZ4ySwk9qRJSOkjBAgXAT0a7M6rwri6QHnL0WxTLX4us4rGu8Ui3kuf1WaZH9DNoeWYs1N3xUclockTkRKaqXnuKjnwSVmsuwxFSlnIPJOiMUUZksiaBq_OUvOkB-dEG7OFiDX9XWj1m62yBHkvZHun8LBr9VW3mt1IrcBdbbtzjWwfn6ioK2c4dbtPFhuYohXsmRDaSekP63Dmlw3A";

        int actualAuthTime = 1567314000;
        int maxAge = 120;

        // set clock to September 1, 2019 5:00:00 AM GMT
        Date clock = new Date(1567314000000L);
        clock.setTime(clock.getTime() + ((maxAge + (DEFAULT_CLOCK_SKEW + 1)) * 1000));

        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                    .withClock(clock)
                    .build()
                    .verify(token, null, maxAge),
            String.format("Authentication Time (auth_time) claim in the ID token indicates that too much time has passed since the last end-user authentication. Current time (%d) is after last auth at (%d)",
                clock.getTime() / 1000, actualAuthTime + maxAge + DEFAULT_CLOCK_SKEW));
    }

    @Test
    public void succeedsWhenMaxSentAndAuthTimeWithinLeeway() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMiLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.AbSYZ_Tu0-ZelCRPuu9jOd9y1M19yIlk8bjSQDVVgAekRZLdRA_T_gi_JeWyFysKZVpRcHC1YJhTH4YH8CCMRTwviq3woIsLmdUecjydyZkHcUlhHXj2DbC15cyELalPNe3T9eZ4ySwk9qRJSOkjBAgXAT0a7M6rwri6QHnL0WxTLX4us4rGu8Ui3kuf1WaZH9DNoeWYs1N3xUclockTkRKaqXnuKjnwSVmsuwxFSlnIPJOiMUUZksiaBq_OUvOkB-dEG7OFiDX9XWj1m62yBHkvZHun8LBr9VW3mt1IrcBdbbtzjWwfn6ioK2c4dbtPFhuYohXsmRDaSekP63Dmlw3A";

        Integer maxAge = 120;

        // set clock to September 1, 2019 5:00:00 AM GMT
        Date clock = new Date(1567314000000L);
        clock.setTime(clock.getTime() + ((maxAge + (DEFAULT_CLOCK_SKEW - 1)) * 1000));

        configureVerifier(token)
                .withClock(clock)
                .build()
                .verify(token, null, maxAge);
    }

    @Test
    public void failsWhenMaxSentButAuthTimeInvalidWithCustomLeeway() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMiLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.AbSYZ_Tu0-ZelCRPuu9jOd9y1M19yIlk8bjSQDVVgAekRZLdRA_T_gi_JeWyFysKZVpRcHC1YJhTH4YH8CCMRTwviq3woIsLmdUecjydyZkHcUlhHXj2DbC15cyELalPNe3T9eZ4ySwk9qRJSOkjBAgXAT0a7M6rwri6QHnL0WxTLX4us4rGu8Ui3kuf1WaZH9DNoeWYs1N3xUclockTkRKaqXnuKjnwSVmsuwxFSlnIPJOiMUUZksiaBq_OUvOkB-dEG7OFiDX9XWj1m62yBHkvZHun8LBr9VW3mt1IrcBdbbtzjWwfn6ioK2c4dbtPFhuYohXsmRDaSekP63Dmlw3A";

        int actualAuthTime = 1567314000;
        Integer maxAge = 120;
        Integer customLeeway = 120;

        // set clock to September 1, 2019 5:00:00 AM GMT
        Date clock = new Date(1567314000000L);
        clock.setTime(clock.getTime() + ((maxAge + customLeeway + 1) * 1000));

        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(token)
                    .withClock(clock)
                    .withLeeway(customLeeway)
                    .build()
                    .verify(token, null, maxAge),
            String.format("Authentication Time (auth_time) claim in the ID token indicates that too much time has passed since the last end-user authentication. Current time (%d) is after last auth at (%d)",
                clock.getTime() / 1000, actualAuthTime + maxAge + customLeeway));
    }

    @Test
    public void succeedsWhenMaxSentAndAuthTimeWithCustomLeeway() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rva2Vucy10ZXN0LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHwxMjM0NTY3ODkiLCJhdWQiOlsidG9rZW5zLXRlc3QtMTIzIiwiZXh0ZXJuYWwtdGVzdC0xMjMiXSwiZXhwIjoxNTY3NDg2ODAwLCJpYXQiOjE1NjczMTQwMDAsIm5vbmNlIjoiYTU5dms1OTIiLCJhenAiOiJ0b2tlbnMtdGVzdC0xMjMiLCJhdXRoX3RpbWUiOjE1NjczMTQwMDB9.AbSYZ_Tu0-ZelCRPuu9jOd9y1M19yIlk8bjSQDVVgAekRZLdRA_T_gi_JeWyFysKZVpRcHC1YJhTH4YH8CCMRTwviq3woIsLmdUecjydyZkHcUlhHXj2DbC15cyELalPNe3T9eZ4ySwk9qRJSOkjBAgXAT0a7M6rwri6QHnL0WxTLX4us4rGu8Ui3kuf1WaZH9DNoeWYs1N3xUclockTkRKaqXnuKjnwSVmsuwxFSlnIPJOiMUUZksiaBq_OUvOkB-dEG7OFiDX9XWj1m62yBHkvZHun8LBr9VW3mt1IrcBdbbtzjWwfn6ioK2c4dbtPFhuYohXsmRDaSekP63Dmlw3A";

        Integer maxAge = 120;
        Integer customLeeway = 120;

        // set clock to September 1, 2019 5:00:00 AM GMT
        Date clock = new Date(1567314000000L);
        clock.setTime(clock.getTime() + ((maxAge + customLeeway - 1) * 1000));

        configureVerifier(token)
                .withClock(clock)
                .withLeeway(customLeeway)
                .build()
                .verify(token, null, maxAge);
    }

    @Test
    public void succeedsWithValidTokenUsingDefaultClock() {
        String token = JWT.create()
                .withSubject("auth0|sdk458fks")
                .withAudience(AUDIENCE)
                .withIssuedAt(getYesterday())
                .withExpiresAt(getTomorrow())
                .withIssuer("https://" + DOMAIN + "/")
                .withClaim("nonce", "nonce")
                .sign(Algorithm.HMAC256("secret"));

        DecodedJWT decodedJWT = JWT.decode(token);
        SignatureVerifier verifier = mock(SignatureVerifier.class);
        when(verifier.verifySignature(token)).thenReturn(decodedJWT);

        IdTokenVerifier.init("https://" + DOMAIN + "/", AUDIENCE, verifier)
                .build()
                .verify(token, "nonce");
    }

    @Test
    public void succeedsWithValidTokenUsingDefaultClockAndHttpDomain() {
        String token = JWT.create()
                .withSubject("auth0|sdk458fks")
                .withAudience(AUDIENCE)
                .withIssuedAt(getYesterday())
                .withExpiresAt(getTomorrow())
                .withIssuer("http://" + DOMAIN + "/")
                .withClaim("nonce", "nonce")
                .sign(Algorithm.HMAC256("secret"));

        DecodedJWT decodedJWT = JWT.decode(token);
        SignatureVerifier verifier = mock(SignatureVerifier.class);
        when(verifier.verifySignature(token)).thenReturn(decodedJWT);

        IdTokenVerifier.init("http://" + DOMAIN + "/", AUDIENCE, verifier)
                .build()
                .verify(token, "nonce");
    }

    @Test
    public void succeedsWithValidTokenUsingDefaultClockAndHttpsDomain() {
        String token = JWT.create()
                .withSubject("auth0|sdk458fks")
                .withAudience(AUDIENCE)
                .withIssuedAt(getYesterday())
                .withExpiresAt(getTomorrow())
                .withIssuer("https://" + DOMAIN + "/")
                .withClaim("nonce", "nonce")
                .sign(Algorithm.HMAC256("secret"));

        DecodedJWT decodedJWT = JWT.decode(token);
        SignatureVerifier verifier = mock(SignatureVerifier.class);
        when(verifier.verifySignature(token)).thenReturn(decodedJWT);

        IdTokenVerifier.init("https://" + DOMAIN + "/", AUDIENCE, verifier)
                .build()
                .verify(token, "nonce");
    }

    @Test
    public void succeedsWhenOrganizationMatchesExpected() {
        String token = JWT.create()
            .withSubject("auth0|sdk458fks")
            .withAudience(AUDIENCE)
            .withIssuedAt(getYesterday())
            .withExpiresAt(getTomorrow())
            .withIssuer("https://" + DOMAIN + "/")
            .withClaim("org_id", "org_123")
            .sign(Algorithm.HMAC256("secret"));

        String jwt = JWT.decode(token).getToken();

        configureVerifier(jwt)
            .withOrganization("org_123")
            .build()
            .verify(jwt);
    }

    @Test
    public void failsWhenOrganizationDoesNotMatchExpected() {
        String token = JWT.create()
            .withSubject("auth0|sdk458fks")
            .withAudience(AUDIENCE)
            .withIssuedAt(getYesterday())
            .withExpiresAt(getTomorrow())
            .withIssuer("https://" + DOMAIN + "/")
            .withClaim("org_id", "org_123")
            .sign(Algorithm.HMAC256("secret"));

        String jwt = JWT.decode(token).getToken();

        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(jwt)
                    .withOrganization("org_abc")
                    .build()
                    .verify(jwt),
            "Organization (org_id) claim mismatch in the ID token; expected \"org_abc\" but found \"org_123\"");
    }

    @Test
    public void failsWhenOrganizationExpectedButNotPresent() {
        String token = JWT.create()
            .withSubject("auth0|sdk458fks")
            .withAudience(AUDIENCE)
            .withIssuedAt(getYesterday())
            .withExpiresAt(getTomorrow())
            .withIssuer("https://" + DOMAIN + "/")
            .sign(Algorithm.HMAC256("secret"));

        String jwt = JWT.decode(token).getToken();

        AssertsUtil.verifyThrows(IdTokenValidationException.class,
            () -> configureVerifier(jwt)
                    .withOrganization("org_123")
                    .build()
                    .verify(jwt),
            "Organization Id (org_id) claim must be a string present in the ID token");
    }

    @Test
    public void failsWhenOrganizationExpectedButClaimIsNotString() {
        String token = JWT.create()
            .withSubject("auth0|sdk458fks")
            .withAudience(AUDIENCE)
            .withIssuedAt(getYesterday())
            .withExpiresAt(getTomorrow())
            .withIssuer("https://" + DOMAIN + "/")
            .withClaim("org_id", 42)
            .sign(Algorithm.HMAC256("secret"));

        String jwt = JWT.decode(token).getToken();

        AssertsUtil.verifyThrows(IdTokenValidationException.class, () -> {
                configureVerifier(jwt)
                    .withOrganization("org_123")
                    .build()
                    .verify(jwt);
        },
            "Organization Id (org_id) claim must be a string present in the ID token");
    }

    @Test
    public void succeedsWhenOrganizationNotSpecifiedButIsPresent() {
        String token = JWT.create()
            .withSubject("auth0|sdk458fks")
            .withAudience(AUDIENCE)
            .withIssuedAt(getYesterday())
            .withExpiresAt(getTomorrow())
            .withIssuer("https://" + DOMAIN + "/")
            .withClaim("org_id", "org_123")
            .sign(Algorithm.HMAC256("secret"));

        String jwt = JWT.decode(token).getToken();

        configureVerifier(jwt)
            .build()
            .verify(jwt);
    }

    private IdTokenVerifier.Builder configureVerifier(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        SignatureVerifier verifier = mock(SignatureVerifier.class);
        when(verifier.verifySignature(token)).thenReturn(decodedJWT);

        return IdTokenVerifier.init("https://" + DOMAIN + "/", AUDIENCE, verifier)
                .withClock(DEFAULT_CLOCK);
    }

    private Date getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        return cal.getTime();
    }

    private Date getTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);

        return cal.getTime();
    }
}
