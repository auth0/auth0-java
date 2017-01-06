package com.auth0.net;

import com.auth0.json.TokenHolder;

public interface AuthRequest extends Request<TokenHolder> {

    void setRealm(String realm);

    void setAudience(String audience);

    void setScope(String scope);
}
