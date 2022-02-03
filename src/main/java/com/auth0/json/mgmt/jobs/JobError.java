package com.auth0.json.mgmt.jobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JobError {

    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("path")
    private String path;

    @JsonCreator
    public JobError(@JsonProperty("code")  String code, @JsonProperty("message") String message, @JsonProperty("path") String path) {
        this.code = code;
        this.message = message;
        this.path = path;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }
}
