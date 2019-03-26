package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = PermissionsPageDeserializer.class)
public class PermissionsPage extends Page<Permission> {

  public PermissionsPage(List<Permission> items) {
    super(items);
  }

  public PermissionsPage(Integer start, Integer length, Integer total, Integer limit, List<Permission> items) {
    super(start, length, total, limit, items);
  }
}
