package com.auth0.net;

import java.util.Map;

public interface SignUpRequest extends Request<Void> {

    void setCustomFields(Map<String, String> customFields);
}
