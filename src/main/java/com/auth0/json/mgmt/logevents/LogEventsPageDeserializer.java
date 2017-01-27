package com.auth0.json.mgmt.logevents;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
class LogEventsPageDeserializer extends StdDeserializer<LogEventsPage> {
    LogEventsPageDeserializer(JavaType valueType) {
        super(valueType);
    }

    LogEventsPageDeserializer() {
        this(null);
    }

    @Override
    public LogEventsPage deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper mapper = new ObjectMapper();
        if (node.isArray()) {
            return new LogEventsPage(getArrayElements((ArrayNode) node, mapper));
        }

        Integer start = getIntegerValue(node.get("start"));
        Integer length = getIntegerValue(node.get("length"));
        Integer total = getIntegerValue(node.get("total"));
        Integer limit = getIntegerValue(node.get("limit"));
        ArrayNode array = (ArrayNode) node.get("logs");

        return new LogEventsPage(start, length, total, limit, getArrayElements(array, mapper));
    }

    private Integer getIntegerValue(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        } else {
            return node.intValue();
        }
    }

    private List<LogEvent> getArrayElements(ArrayNode array, ObjectMapper mapper) throws IOException {
        CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, LogEvent.class);
        return mapper.readerFor(type).readValue(array);
    }
}
