/*
 * UserProfile.java
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

package com.auth0.authentication.result;

import com.auth0.util.CheckHelper;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Class that holds the information of a user's profile
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile {

    protected String id;
    protected String name;
    protected String nickname;
    protected String email;
    protected String pictureURL;
    protected Date createdAt;
    protected Map<String, Object> extraInfo;
    protected List<UserIdentity> identities;

    protected UserProfile(UserProfile userProfile) {
        id = userProfile.id;
        name = userProfile.name;
        nickname = userProfile.nickname;
        email = userProfile.email;
        pictureURL = userProfile.pictureURL;
        createdAt = userProfile.createdAt;
        extraInfo = userProfile.extraInfo;
        identities = userProfile.identities;
    }

    protected UserProfile() {

    }

    @JsonCreator
    @SuppressWarnings("unchecked")
    public UserProfile(Map<String, Object> values) {
        CheckHelper.checkArgument(values != null, "must supply non-null values");
        HashMap<String, Object> info = new HashMap<String, Object>(values);
        String id = (String) info.remove("user_id");
        CheckHelper.checkArgument(id != null, "profile must have a user id");
        this.id = id;
        this.name = (String) info.remove("name");
        this.nickname = (String) info.remove("nickname");
        this.email = (String) info.remove("email");
        this.pictureURL = (String) info.remove("picture");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            String created_at = (String) info.remove("created_at");
            this.createdAt = created_at != null ? sdf.parse(created_at) : null;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid created_at value", e);
        }
        this.identities = buildIdentities((List<Map<String, Object>>) info.remove("identities"));
        this.extraInfo = info;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns extra information of the profile.
     * @return
     */
    public Map<String, Object> getExtraInfo() {
        return new HashMap<>(extraInfo);
    }

    /**
     * List of the identities from a Identity Provider associated to the user.
     * @return
     */
    public List<UserIdentity> getIdentities() {
        return identities;
    }

    private List<UserIdentity> buildIdentities(List<Map<String, Object>> values) {
        if (values == null) {
            return Collections.emptyList();
        }
        List<UserIdentity> identities = new ArrayList<>(values.size());
        for (Map<String, Object> value: values) {
            identities.add(new UserIdentity(value));
        }
        return identities;
    }
}
