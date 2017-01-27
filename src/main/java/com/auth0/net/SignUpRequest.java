package com.auth0.net;

import java.util.Map;

/**
 * Class that represents a Create User call.
 */
public interface SignUpRequest extends Request<Void> {

    /**
     * Setter for the additional fields to set when creating a user.
     *
     * @param customFields the list of custom fields.
     */
    void setCustomFields(Map<String, String> customFields);
}
