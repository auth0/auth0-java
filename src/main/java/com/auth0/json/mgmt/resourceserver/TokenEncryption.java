package com.auth0.json.mgmt.resourceserver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents the token encryption associated with a {@link ResourceServer}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenEncryption {

    @JsonProperty("format")
    private String format;
    @JsonProperty("encryption_key")
    private EncryptionKey encryptionKey;

    /**
     * Create a new instance.
     * @param format the value of the {@code format} field.
     * @param encryptionKey the value of the {@code encryption_key} field.
     */
    @JsonCreator
    public TokenEncryption(@JsonProperty("format") String format, @JsonProperty("encryption_key") EncryptionKey encryptionKey) {
        this.format = format;
        this.encryptionKey = encryptionKey;
    }
    /**
     * @return the value of the {@code format} field.
     */
    public String getFormat() {
        return format;
    }

    /**
     * @return the value of the {@code encryption_key} field.
     */
    public EncryptionKey getEncryptionKey() {
        return encryptionKey;
    }
}
