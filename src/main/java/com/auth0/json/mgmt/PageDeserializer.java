package com.auth0.json.mgmt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

public abstract class PageDeserializer<T, U> extends StdDeserializer<T> {

    private final String itemsPropertyName;
    private final Class<U> uClazz;
    private static final ObjectMapper mapper = new ObjectMapper();

    protected PageDeserializer(Class<U> clazz, String arrayName) {
        super(Object.class);
        this.uClazz = clazz;
        this.itemsPropertyName = arrayName;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.isArray()) {
            return createPage(getArrayElements((ArrayNode) node));
        }

        Integer start = getIntegerValue(node.get("start"));
        Integer length = getIntegerValue(node.get("length"));
        Integer total = getIntegerValue(node.get("total"));
        Integer limit = getIntegerValue(node.get("limit"));
        ArrayNode array = (ArrayNode) node.get(itemsPropertyName);

        return createPage(start, length, total, limit, getArrayElements(array));
    }

    protected abstract T createPage(List<U> items);

    protected abstract T createPage(Integer start, Integer length, Integer total, Integer limit, List<U> items);

    private Integer getIntegerValue(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        } else {
            return node.intValue();
        }
    }

    private List<U> getArrayElements(ArrayNode array) throws IOException {
        CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, uClazz);
        return mapper.readerFor(type).readValue(array);
    }
}
