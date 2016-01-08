package com.auth0.java.core;

import com.auth0.java.util.CheckHelper;
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

    private String id;
    private String name;
    private String nickname;
    private String email;
    private String pictureURL;
    private Date createdAt;
    private Map<String, Object> extraInfo;
    private List<UserIdentity> identities;

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
