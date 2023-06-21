package com.auth0;

import org.junit.jupiter.api.function.Executable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertsUtil {

    public static <T extends Throwable> T verifyThrows(Class<T> expectedType, Executable executable) {
        return assertThrows(expectedType, executable);
    }

    public static <T extends Throwable> T verifyThrows(Class<T> expectedType, Executable executable, String message) {
        T result = assertThrows(expectedType, executable);
        assertThat(result.getMessage(), is(message));
        return result;
    }
}
