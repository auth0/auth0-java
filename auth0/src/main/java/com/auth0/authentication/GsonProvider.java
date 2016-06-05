package com.auth0.authentication;

import com.auth0.authentication.result.JsonRequiredTypeAdapterFactory;
import com.auth0.authentication.result.UserProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

abstract class GsonProvider {

    static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new JsonRequiredTypeAdapterFactory())
                .registerTypeAdapter(UserProfile.class, new UserProfileDeserializer())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }
}
