package com.auth0.client.mgmt;

import java.util.List;

public class EntityManager {

    private static EntityManager instance = new EntityManager();

    private OrganizationsEntity organizationsEntity;

    private List<BaseManagementEntity> entities;

    private EntityManager() {}

    static EntityManager getInstance() {
        return instance;
    }

    void updateApiToken() {
        entities.clear();
    }

    // TODO do not have OkHttp dependency here
//    OrganizationsEntity getOrganizationsEntity(Auth0HttpClient client, HttpUrl baseurl, String apiToken) {
//        if (organizationsEntity == null) {
//            organizationsEntity = new OrganizationsEntity(client, baseurl, apiToken);
//            entities.add(organizationsEntity);
//        }
//        return organizationsEntity;
//    }
}
