package com.auth0.client;

import com.auth0.utils.Asserts;
import java.util.Collections;
import java.util.Set;

/**
 * Used to configure the HTTP Logging options.
 */
public class LoggingOptions {

    public enum LogLevel {

        /**
         * No logging.
         */
        NONE,

        /**
         * Logs request and response lines.
         */
        BASIC,

        /**
         * Logs request and response lines, along with their respective headers. Note that headers may contain
         * sensitive information; see {@linkplain #headersToRedact}
         */
        HEADERS,

        /**
         * Logs request and response lines, along with their respective headers and bodies. Note that headers and bodies
         * may contain sensitive information; see {@linkplain #headersToRedact} for header redaction, but that only
         * applies to headers. This should only be used in controlled or non-production environments.
         */
        BODY
    }

    private LogLevel logLevel;
    private Set<String> headersToRedact = Collections.emptySet();

    /**
     * Create a new instance using the specified {@linkplain LogLevel}
     * @param logLevel the log level to set. Must not be null.
     */
    public LoggingOptions(LogLevel logLevel) {
        Asserts.assertNotNull(logLevel, "logLevel");
        this.logLevel = logLevel;
    }

    /**
     * @return the log level of this instance.
     */
    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    /**
     * @return the headers that should be redacted from the output log.
     */
    public Set<String> getHeadersToRedact() {
        return headersToRedact;
    }

    /**
     * Sets the headers to redact from the log. When using {@code HEADERS} or {@code BODY} logging levels, there is the
     * potential of leaking sensitive information such as "Authorization" or "Cookie" headers. Note that this does not
     * redact any of the body contents from being logged, so care must always be taken with {@code HEADERS} or {@code BODY}
     * log levels.
     *
     * @param headersToRedact the Set of headers to redact.
     */
    public void setHeadersToRedact(Set<String> headersToRedact) {
        this.headersToRedact = headersToRedact;
    }
}
