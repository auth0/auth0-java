package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionConfig {
    @JsonProperty("name")
    private String name;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("is_domain_connection")
    private boolean isDomainConnection;
    @JsonProperty("show_as_button")
    private boolean showAsButton;
    @JsonProperty("metadata")
    private Object metadata;
    @JsonProperty("options")
    private Options options;

    /**
     * Creates a new instance of the ConnectionConfig class.
     * @return the new instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name.
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the display name.
     * @return the display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Setter for the display name.
     * @param displayName the display name to set.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Getter for the domain connection.
     * @return the domain connection.
     */
    public boolean isDomainConnection() {
        return isDomainConnection;
    }

    /**
     * Setter for the domain connection.
     * @param domainConnection the domain connection to set.
     */
    public void setDomainConnection(boolean domainConnection) {
        isDomainConnection = domainConnection;
    }

    /**
     * Getter for the show as button.
     * @return the show as button.
     */
    public boolean isShowAsButton() {
        return showAsButton;
    }

    /**
     * Setter for the show as button.
     * @param showAsButton the show as button to set.
     */
    public void setShowAsButton(boolean showAsButton) {
        this.showAsButton = showAsButton;
    }

    /**
     * Getter for the metadata.
     * @return the metadata.
     */
    public Object getMetadata() {
        return metadata;
    }

    /**
     * Setter for the metadata.
     * @param metadata the metadata to set.
     */
    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    /**
     * Getter for the options.
     * @return the options.
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Setter for the options.
     * @param options the options to set.
     */
    public void setOptions(Options options) {
        this.options = options;
    }
}
