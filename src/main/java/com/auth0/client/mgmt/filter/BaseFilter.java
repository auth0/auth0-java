package com.auth0.client.mgmt.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class that keeps the parameters that each filter requires.
 * <p>
 * This class is not thread-safe:
 * It makes use of {@link HashMap} for storing the parameters. Make sure to not call the builder methods
 * from a different or un-synchronized thread.
 */
public abstract class BaseFilter {
    protected final Map<String, Object> parameters = new HashMap<>();

    public Map<String, Object> getAsMap() {
        return parameters;
    }
}
