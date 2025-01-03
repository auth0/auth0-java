package com.auth0.json.mgmt.selfserviceprofiles;

import com.auth0.json.mgmt.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = SelfServiceProfileResponsePageDeserializer.class)
public class SelfServiceProfileResponsePage extends Page<SelfServiceProfileResponse> {
    public SelfServiceProfileResponsePage(List<SelfServiceProfileResponse> items) {
        super(items);
    }

    public SelfServiceProfileResponsePage(Integer start, Integer length, Integer total, Integer limit, List<SelfServiceProfileResponse> items) {
        super(start, length, total, limit, items);
    }
}
