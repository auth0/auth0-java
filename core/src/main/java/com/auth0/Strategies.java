/*
 * Strategies.java
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

package com.auth0;

import static com.auth0.Strategies.Type.DATABASE;
import static com.auth0.Strategies.Type.ENTERPRISE;
import static com.auth0.Strategies.Type.PASSWORDLESS;
import static com.auth0.Strategies.Type.SOCIAL;

/**
 * An enum with all strategies available in Auth0
 */
public enum Strategies {
    Auth0("auth0", DATABASE),

    Email("email", PASSWORDLESS),
    SMS("sms", PASSWORDLESS),

    Amazon("amazon", SOCIAL),
    AOL("aol", SOCIAL),
    Baidu("baidu", SOCIAL),
    Box("box", SOCIAL),
    Dwolla("dwolla", SOCIAL),
    EBay("ebay", SOCIAL),
    Evernote("evernote", SOCIAL),
    EvernoteSandbox("evernote-sandbox", SOCIAL),
    Exact("exact", SOCIAL),
    Facebook("facebook", SOCIAL),
    Fitbit("fitbit", SOCIAL),
    Github("github", SOCIAL),
    GooglePlus("google-oauth2", SOCIAL),
    Instagram("instagram", SOCIAL),
    Linkedin("linkedin", SOCIAL),
    Miicard("miicard", SOCIAL),
    Paypal("paypal", SOCIAL),
    PlanningCenter("planningcenter", SOCIAL),
    RenRen("renren", SOCIAL),
    Salesforce("salesforce", SOCIAL),
    SalesforceSandbox("salesforce-sandbox", SOCIAL),
    Shopify("shopify", SOCIAL),
    Soundcloud("soundcloud", SOCIAL),
    TheCity("thecity", SOCIAL),
    TheCitySandbox("thecity-sandbox", SOCIAL),
    ThirtySevenSignals("thirtysevensignals", SOCIAL),
    Twitter("twitter", SOCIAL),
    VK("vkontakte", SOCIAL),
    Weibo("weibo", SOCIAL),
    WindowsLive("windowslive", SOCIAL),
    Wordpress("wordpress", SOCIAL),
    Yahoo("yahoo", SOCIAL),
    Yammer("yammer", SOCIAL),
    Yandex("yandex", SOCIAL),

    ActiveDirectory("ad", ENTERPRISE),
    ADFS("adfs", ENTERPRISE),
    Auth0LDAP("auth0-adldap", ENTERPRISE),
    Custom("custom", ENTERPRISE),
    GoogleApps("google-apps", ENTERPRISE),
    GoogleOpenId("google-openid", ENTERPRISE),
    IP("ip", ENTERPRISE),
    MSCRM("mscrm", ENTERPRISE),
    Office365("office365", ENTERPRISE),
    PingFederate("pingfederate", ENTERPRISE),
    SAMLP("samlp", ENTERPRISE),
    Sharepoint("sharepoint", ENTERPRISE),
    Waad("waad", ENTERPRISE),

    UnknownSocial("unknown-social", SOCIAL);

    private String name;
    private Type type;

    Strategies(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public static Strategies fromName(String name) {
        Strategies strategy = null;
        for (Strategies str: values()) {
            if (str.getName().equals(name)) {
                strategy = str;
                break;
            }
        }

        // if strategy not found, assume it's a new social type
        if (strategy == null)
            strategy = UnknownSocial;

        return strategy;
    }

    public enum Type {
        DATABASE,
        SOCIAL,
        ENTERPRISE,
        PASSWORDLESS
    }
}
