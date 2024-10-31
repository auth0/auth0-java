package com.auth0.json.mgmt.keys;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

public class EncryptionKeysPageDeserializer extends PageDeserializer<EncryptionKeysPage, EncryptionKey> {

    protected EncryptionKeysPageDeserializer() {
        super(EncryptionKey.class, "keys");
    }

    @Override
    protected EncryptionKeysPage createPage(List<EncryptionKey> items) {
        return new EncryptionKeysPage(items);
    }

    @Override
    protected EncryptionKeysPage createPage(Integer start, Integer length, Integer total, Integer limit, List<EncryptionKey> items) {
        return new EncryptionKeysPage(start, length, total, limit, items);
    }

}
