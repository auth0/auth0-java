package com.auth0.json.mgmt.keys;

import com.auth0.json.mgmt.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = EncryptionKeysPageDeserializer.class)
public class EncryptionKeysPage extends Page<EncryptionKey> {

    public EncryptionKeysPage(List<EncryptionKey> items) {
        super(items);
    }

    public EncryptionKeysPage(Integer start, Integer length, Integer total, Integer limit, List<EncryptionKey> items) {
        super(start, length, total, limit, items);
    }

}
