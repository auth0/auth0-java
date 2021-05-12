package com.auth0.client;

/**
 * Used to configure additional configuration options when customizing the API client instance.
 */
public class HttpOptions {

    private ProxyOptions proxyOptions;
    private int connectTimeout = 10;
    private int readTimeout = 10;

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


    /**
     * @return the connect timeout, in seconds
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets the value of the connect timeout, in seconds. Defaults to ten seconds. A value of zero results in no connect timeout.
     * Negative numbers will be treated as zero.
     * @param connectTimeout the value of the connect timeout to use.
     */
    public void setConnectTimeout(int connectTimeout) {
        if (connectTimeout < 0) {
            connectTimeout = 0;
        }
        this.connectTimeout = connectTimeout;
    }

    /**
     * @return the read timeout, in seconds
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * Sets the value of the read timeout, in seconds. Defaults to ten seconds. A value of zero results in no read timeout.
     * Negative numbers will be treated as zero.
     *
     * @param readTimeout the value of the read timeout to use.
     */
    public void setReadTimeout(int readTimeout) {
        if (readTimeout < 0) {
            readTimeout = 0;
        }
        this.readTimeout = readTimeout;
    }
}
