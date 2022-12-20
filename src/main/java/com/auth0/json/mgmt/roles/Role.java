package com.auth0.json.mgmt.roles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Role object. Related to the {@link com.auth0.client.mgmt.RolesEntity} entity.
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  /**
   * Getter for the role's unique identifier.
   *
   * @return the role id.
   */
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  /**
   * Getter for the role's name.
   *
   * @return the name of the role.
   */
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  /**
   * Setter for the role's name.
   *
   * @param name the name of the role to set.
   */
  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  /**
   * The getter for the role's description.
   *
   * @return the description of the role.
   */
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  /**
   * Setter for the role's description.
   *
   * @param description the description of the role to set.
   */
  @JsonProperty("description")
  public void setDescription(String description) {
    this.description = description;
  }
}
