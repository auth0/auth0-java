package com.auth0.net;

import com.auth0.json.ObjectMapperProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation of the client information sent by this SDK on every request.
 * <p>
 * This class is thread-safe.
 *
 * @see TelemetryInterceptor
 */
@SuppressWarnings("WeakerAccess")
public class Telemetry {
    static final String HEADER_NAME = "Auth0-Client";

    private static final String JAVA_SPECIFICATION_VERSION = "java.specification.version";
    private static final String NAME_KEY = "name";
    private static final String VERSION_KEY = "version";
    private static final String LIBRARY_VERSION_KEY = "auth0-java";
    private static final String ENV_KEY = "env";
    private static final String JAVA_KEY = "java";

    private final String name;
    private final String version;
    private final String libraryVersion;
    private final Map<String, String> env;
    private final String value;

    public Telemetry(String name, String version) {
        this(name, version, null);
    }

    public Telemetry(String name, String version, String libraryVersion) {
        this.name = name;
        this.version = version;
        this.libraryVersion = libraryVersion;

        if (name == null) {
            env = Collections.emptyMap();
            value = null;
            return;
        }

        Map<String, Object> values = new HashMap<>();
        values.put(NAME_KEY, name);
        if (version != null) {
            values.put(VERSION_KEY, version);
        }

        HashMap<String, String> tmpEnv = new HashMap<>();
        tmpEnv.put(JAVA_KEY, getJDKVersion());
        if (libraryVersion != null) {
            tmpEnv.put(LIBRARY_VERSION_KEY, libraryVersion);
        }
        this.env = Collections.unmodifiableMap(tmpEnv);
        values.put(ENV_KEY, env);

        String tmpValue;
        try {
            String json = ObjectMapperProvider.getMapper().writeValueAsString(values);
            tmpValue = Base64.getUrlEncoder().encodeToString(json.getBytes());
        } catch (JsonProcessingException e) {
            tmpValue = null;
            e.printStackTrace();
        }
        value = tmpValue;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    // Visible for testing
    String getLibraryVersion() {
        return libraryVersion;
    }

    // Visible for testing
    Map<String, String> getEnvironment() {
        return env;
    }

    public String getValue() {
        return value;
    }

    private String getJDKVersion() {
        String version;
        try {
            version = System.getProperty(JAVA_SPECIFICATION_VERSION);
        } catch (Exception ignored) {
            version = Runtime.class.getPackage().getSpecificationVersion();
        }
        return version;
    }
}
