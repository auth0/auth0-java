package com.auth0.client;

/**
 * Used to configure additional configuration options when customizing the API client instance.
 */
public class HttpOptions {

    private ProxyOptions proxyOptions;

    /**
     * Getter for the Proxy configuration options
     *
     * @return the Proxy configuration options if set, null otherwise.
     */
    public ProxyOptions getProxyOptions() {
        return proxyOptions;
    }

    /**
     * Setter for the Proxy configuration options
     *
     * @param proxyOptions the Proxy configuration options
     */
    public void setProxyOptions(ProxyOptions proxyOptions) {
        this.proxyOptions = proxyOptions;
    }
}
