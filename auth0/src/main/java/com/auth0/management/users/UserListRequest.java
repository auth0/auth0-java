/*
 * UserListRequest.java
 *
 * Copyright (c) 2016 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0.management.users;


import com.auth0.Auth0Exception;
import com.auth0.callback.BaseCallback;
import com.auth0.management.result.UsersListPage;
import com.auth0.request.ParameterizableRequest;
import com.auth0.request.Request;

import java.util.Arrays;
import java.util.List;


/**
 * Request to get a list of users. The results can be the result of a search
 */
public class UserListRequest implements Request<UsersListPage> {

    private static final String KEY_PAGE = "page";
    private static final String KEY_PER_PAGE = "per_page";
    private static final String KEY_SORT = "sort";
    private static final String KEY_FIELDS = "fields";
    private static final String KEY_INCLUDE_FIELDS = "include_fields";
    private static final String KEY_INCLUDE_TOTALS = "include_totals";
    private static final String KEY_QUERY = "q";
    private static final String KEY_SEARCH_ENGINE = "search_engine";

    private static final String SEARCH_ENGINE_V2 = "v2";

    public static final String SORT_ASC = "1";
    public static final String SORT_DESC = "-1";

    private final ParameterizableRequest<UsersListPage> request;

    public UserListRequest(ParameterizableRequest<UsersListPage> request) {
        this.request = request;
        this.request.addQueryParameter(KEY_INCLUDE_TOTALS, String.valueOf(true));
        this.request.addQueryParameter(KEY_SEARCH_ENGINE, SEARCH_ENGINE_V2);
    }

    @Override
    public void start(BaseCallback<UsersListPage> callback) {
        request.start(callback);
    }

    @Override
    public UsersListPage execute() throws Auth0Exception {
        return request.execute();
    }

    /**
     * Sets the page number to get. Zero based
     *
     * @param page the page number
     * @return itself
     */
    public UserListRequest page(int page) {
        request.addQueryParameter(KEY_PAGE, String.valueOf(page));
        return this;
    }

    /**
     * Sets the amount of entries per page.
     *
     * @param perPage the amount of entries per page. Default is 50, with a maximum of 100
     * @return itself
     */
    public UserListRequest perPage(int perPage) {
        request.addQueryParameter(KEY_PER_PAGE, String.valueOf(perPage));
        return this;
    }

    /**
     * Sets the field and order to use for sorting.
     *
     * @param sort the sort field
     * @param sortOrder the sort order: SORT_ASC ("1") or SORT_DESC ("-1")
     * @return itself
     */
    public UserListRequest sortBy(String sort, String sortOrder) {
        request.addQueryParameter(KEY_SORT, String.format("%s:%s", sort, sortOrder));
        return this;
    }

    /**
     * Sets the query string
     *
     * @param query the query in Lucene query string syntax
     * @return itself
     */
    public UserListRequest search(String query) {
        request.addQueryParameter(KEY_QUERY, query);
        return this;
    }

    /**
     * Excludes these fields from the result
     *
     * @param fields the list of fields to exclude from the result
     * @return itself
     */
    public UserListRequest exclude(String... fields) {
        setIncludeFields(false, fields);
        return this;
    }

    /**
     * Only include these fields in the result
     *
     * @param fields the list of fields to include in the result
     * @return itself
     */
    public UserListRequest onlyInclude(String... fields) {
        setIncludeFields(true, fields);
        return this;
    }

    private void setIncludeFields(boolean includeFields, String... fieldsArgs) {
        request.addQueryParameter(KEY_INCLUDE_FIELDS, String.valueOf(includeFields));
        StringBuilder stringBuilder = new StringBuilder();
        List<String> fields = Arrays.asList(fieldsArgs);
        for (String field : fields) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(field);
        }
        request.addQueryParameter(KEY_FIELDS, stringBuilder.toString());
    }
}
