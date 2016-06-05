package com.auth0.authentication;

import com.auth0.authentication.result.UserIdentity;
import com.auth0.authentication.result.UserProfile;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

class UserProfileDeserializer implements JsonDeserializer<UserProfile> {
    @Override
    public UserProfile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject() || json.isJsonNull()) {
            throw new JsonParseException("user profile json is not a valid json object");
        }

        JsonObject object = json.getAsJsonObject();
        final String id = requiredValue("user_id", String.class, object, context);
        final String name = requiredValue("name", String.class, object, context);
        final String nickname = requiredValue("nickname", String.class, object, context);
        final String picture = requiredValue("picture", String.class, object, context);

        final String email = context.deserialize(object.get("email"), String.class);
        final Date createdAt = context.deserialize(object.get("created_at"), Date.class);

        final Type identitiesType = new TypeToken<List<UserIdentity>>(){}.getType();
        final List<UserIdentity> identities = context.deserialize(object.get("identities"), identitiesType);

        final Type metadataType = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> userMetadata = context.deserialize(object.get("user_metadata"), metadataType);
        Map<String, Object> appMetadata = context.deserialize(object.get("app_metadata"), metadataType);
        return new UserProfile(id, name, nickname, picture, email, userMetadata, appMetadata, createdAt, identities, null);
    }

    private <T> T requiredValue(String name, Type type, JsonObject object, JsonDeserializationContext context) throws JsonParseException {
        T value = context.deserialize(object.get(name), type);
        if (value == null) {
            throw new JsonParseException(String.format("Missing required attribute %s", name));
        }
        return value;
    }
}
