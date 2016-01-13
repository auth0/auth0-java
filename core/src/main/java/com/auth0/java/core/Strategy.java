/*
 * Strategy.java
 *
 * Copyright (c) 2015 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0.java.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class with Auth0 authentication strategy info
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Strategy {

    private String name;
    private List<Connection> connections;
    private Strategies strategyMetadata;

    @JsonCreator
    public Strategy(@JsonProperty(value = "name", required = true) String name,
                    @JsonProperty(value = "connections", required = true) List<Connection> connections) {
        this.name = name;
        this.connections = connections;
        this.strategyMetadata = Strategies.fromName(name);
    }

    public String getName() {
        return name;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Strategies.Type getType() {
        return this.strategyMetadata.getType();
    }

    public boolean isResourceOwnerEnabled() {
        return Strategies.ActiveDirectory.getName().equals(name)
                || Strategies.ADFS.getName().equals(name)
                || Strategies.Waad.getName().equals(name);
    }
}
