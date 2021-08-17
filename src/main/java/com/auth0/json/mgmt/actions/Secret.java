package com.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;

/**
 * Represents a secret of an action.
 * @see Action
 * @see com.auth0.client.mgmt.ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Secret {

    @JsonProperty("name")
    private String name;
    @JsonProperty("value")
    private String value;
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date updatedAt;

    /**
     * Creates a new instance.
     * @param name the name of the secret, e.g., "API_KEY"
     * @param value the value of the secret.
     */
    @JsonCreator
    public Secret(@JsonProperty("name") String name, @JsonProperty("value") String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Creates a new instance.
     */
    public Secret() {}

    /**
     * @return the name of this secret.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of this secret.
     * @param name the name of the secret, e.g., "API_KEY"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of this secret.
     * <p>
     * <strong>Secret values will never be returned by the API. This will only return a value if the instance is being
     * used to create or update a secret.</strong>
     * </p>
     * @return the value of this secret.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value of this secret.
     * @param value the value of this secret.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the date this secret was updated at.
     */
    public Date getUpdatedAt() {
        return this.updatedAt;
    }


}
