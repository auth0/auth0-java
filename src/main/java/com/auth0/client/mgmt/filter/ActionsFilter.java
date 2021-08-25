package com.auth0.client.mgmt.filter;

/**
 * Used to filter the results when getting all actions.
 * @see com.auth0.client.mgmt.ActionsEntity#list(ActionsFilter)
 */
public class ActionsFilter extends BaseFilter {

    /**
     * Filters the result by a trigger ID
     * @param triggerId the trigger ID to filter the results by
     * @return this filter instance
     */
    public ActionsFilter withTriggerId(String triggerId) {
        parameters.put("triggerId", triggerId);
        return this;
    }

    /**
     * Filters the result by the name of an action
     * @param actionName the name of the action to filter by
     * @return this filter instance
     */
    public ActionsFilter withActionName(String actionName) {
        parameters.put("actionName", actionName);
        return this;
    }

    /**
     * Filters the result by its deployment status
     * @param deployed when {@code true}, only deployed actions will be returned
     * @return this filter instance
     */
    public ActionsFilter withDeployed(Boolean deployed) {
        parameters.put("deployed", deployed);
        return this;
    }

    /**
     * Filters the result by its installation status
     * @param installed when {@code true}, only installed actions will be returned
     * @return this filter instance
     */
    public ActionsFilter withInstalled(Boolean installed) {
        parameters.put("installed", installed);
        return this;
    }

    /**
     * Filter by page
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public ActionsFilter withPage(int pageNumber, int amountPerPage) {
        parameters.put("page", pageNumber);
        parameters.put("per_page", amountPerPage);
        return this;
    }
}
