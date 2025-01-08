package com.auth0.json.mgmt.selfserviceprofiles;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;


public class SelfServiceProfileResponsePageDeserializer extends PageDeserializer<SelfServiceProfileResponsePage, SelfServiceProfileResponse> {

    protected SelfServiceProfileResponsePageDeserializer() {
        super(SelfServiceProfileResponse.class, "self_service_profiles");
    }

    @Override
    protected SelfServiceProfileResponsePage createPage(List<SelfServiceProfileResponse> items) {
        return new SelfServiceProfileResponsePage(items);
    }

    @Override
    protected SelfServiceProfileResponsePage createPage(Integer start, Integer length, Integer total, Integer limit, List<SelfServiceProfileResponse> items) {
        return new SelfServiceProfileResponsePage(start, length, total, limit, items);
    }
}
