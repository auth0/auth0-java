package com.auth0.client.mgmt.filter;

import com.auth0.json.mgmt.jobs.UsersExportField;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class UsersExportFilter extends BaseFilter {

    public static final String KEY_FIELDS = "fields";
    public static final String KEY_FORMAT = "format";
    public static final String KEY_LIMIT = "limit";

    /**
     * Changes the output format of the exports file
     *
     * @param format the format of the exports file. Typically 'csv' or 'json'.
     * @return this filter instance
     */
    public UsersExportFilter withFormat(String format) {
        parameters.put(KEY_FORMAT, format);
        return this;
    }

    /**
     * Limits the amount of results
     *
     * @param limit the amount of results to include
     * @return this filter instance
     */
    public UsersExportFilter withLimit(int limit) {
        parameters.put(KEY_LIMIT, limit);
        return this;
    }

    /**
     * Selects the fields to include in each user export
     *
     * @param fields the list of fields to include in each user export
     * @return this filter instance
     */
    public UsersExportFilter withFields(List<UsersExportField> fields) {
        parameters.put(KEY_FIELDS, fields);
        return this;
    }

}
