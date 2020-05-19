package com.auth0.json.auth;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds the Information related to a User's Access Token. Obtained after a call to {@link com.auth0.client.auth.AuthAPI#userInfo(String)},
 * {@link com.auth0.client.auth.AuthAPI#signUp(String, char[], String)} or {@link com.auth0.client.auth.AuthAPI#signUp(String, String, char[], String)}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo implements Serializable {

    private Map<String, Object> values;

    UserInfo() {
        values = new HashMap<>();
    }

    @JsonAnySetter
    void setValue(String key, Object value) {
        values.put(key, value);
    }

    /**
     * Getter for the values contained in this object
     *
     * @return the values contained in the object.
     */
    @JsonAnyGetter
    public Map<String, Object> getValues() {
        return values;
    }
}
