/*
 * UserLogsRequest.java
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
import com.auth0.management.result.LogsListPage;
import com.auth0.request.ParameterizableRequest;
import com.auth0.request.Request;


/**
 * Request to get a user's logs.
 */
public class UserLogsRequest implements Request<LogsListPage> {

    private static final String KEY_PAGE = "page";
    private static final String KEY_PER_PAGE = "per_page";
    private static final String KEY_SORT = "sort";
    private static final String KEY_INCLUDE_TOTALS = "include_totals";

    private final ParameterizableRequest<LogsListPage> request;

    public static final String SORT_ASC = "1";
    public static final String SORT_DESC = "-1";

    public UserLogsRequest(ParameterizableRequest<LogsListPage> request) {
        this.request = request;
        this.request.addQueryParameter(KEY_INCLUDE_TOTALS, String.valueOf(true));
    }

    @Override
    public void start(BaseCallback<LogsListPage> callback) {
        request.start(callback);
    }

    @Override
    public LogsListPage execute() throws Auth0Exception {
        return request.execute();
    }

    /**
     * Sets the page number to get. Zero based
     *
     * @param page the page number
     * @return itself
     */
    public UserLogsRequest page(int page) {
        request.addQueryParameter(KEY_PAGE, String.valueOf(page));
        return this;
    }

    /**
     * Sets the amount of entries per page.
     *
     * @param perPage the amount of entries per page. Default is 50, with a maximum of 100
     * @return itself
     */
    public UserLogsRequest perPage(int perPage) {
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
    public UserLogsRequest sortBy(String sort, String sortOrder) {
        request.addQueryParameter(KEY_SORT, String.format("%s:%s", sort, sortOrder));
        return this;
    }
}
