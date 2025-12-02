package com.auth0.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
class PasswordStrengthErrorParser {
    private static final String RULE_TYPE_LENGTH_AT_LEAST = "lengthAtLeast";
    private static final String RULE_TYPE_CONTAINS_AT_LEAST = "containsAtLeast";
    private static final String RULE_TYPE_SHOULD_CONTAIN = "shouldContain";
    private static final String RULE_TYPE_IDENTICAL_CHARS = "identicalChars";

    private static final String KEY_RULES = "rules";
    private static final String KEY_CODE = "code";
    private static final String KEY_VERIFIED = "verified";
    private static final String KEY_FORMAT = "format";
    private static final String KEY_ITEMS = "items";
    private static final String KEY_MESSAGE = "message";

    private String description;

    PasswordStrengthErrorParser(Map<String, Object> descriptionMap) {
        List<Map<String, Object>> rules = (List<Map<String, Object>>) descriptionMap.get(KEY_RULES);
        parseRules(rules);
    }

    public String getDescription() {
        return description;
    }

    private void parseRules(List<Map<String, Object>> rules) {
        List<String> items = new ArrayList<>();
        for (Map<String, Object> rule : rules) {
            boolean isVerified = (boolean) rule.get(KEY_VERIFIED);
            if (isVerified) {
                continue;
            }
            String code = (String) rule.get(KEY_CODE);
            switch (code) {
                case RULE_TYPE_LENGTH_AT_LEAST:
                    items.add(asLengthAtLeast(rule));
                    break;
                case RULE_TYPE_IDENTICAL_CHARS:
                    items.add(asIdenticalChars(rule));
                    break;
                case RULE_TYPE_CONTAINS_AT_LEAST:
                case RULE_TYPE_SHOULD_CONTAIN:
                    items.add(asContainsCharset(rule));
                    break;
            }
        }

        this.description = joinStrings("; ", items);
    }

    private String asLengthAtLeast(Map<String, Object> rule) {
        List<Number> length = (List<Number>) rule.get(KEY_FORMAT);
        String message = (String) rule.get(KEY_MESSAGE);
        return String.format(message, length.get(0).intValue());
    }

    private String asContainsCharset(Map<String, Object> rule) {
        List<Map<String, Object>> itemsList = (List<Map<String, Object>>) rule.get(KEY_ITEMS);
        List<String> items = new ArrayList<>();
        for (Map<String, Object> i : itemsList) {
            items.add((String) i.get(KEY_MESSAGE));
        }
        String requiredItems = joinStrings(", ", items);
        String message = (String) rule.get(KEY_MESSAGE);

        if (rule.containsKey(KEY_FORMAT)) {
            List<Number> quantity = (List<Number>) rule.get(KEY_FORMAT);
            message = String.format(
                    message, quantity.get(0).intValue(), quantity.get(1).intValue());
        }

        return String.format("%s %s", message, requiredItems);
    }

    private String asIdenticalChars(Map<String, Object> rule) {
        List<Object> example = (List<Object>) rule.get(KEY_FORMAT);
        Number count = (Number) example.get(0);
        String message = (String) rule.get(KEY_MESSAGE);
        return String.format(message, count.intValue(), example.get(1));
    }

    private String joinStrings(String delimiter, List<String> items) {
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < items.size() - 1; i++) {
            sb.append(items.get(i)).append(delimiter);
        }
        sb.append(items.get(i));
        return sb.toString();
    }
}
