/*
 * UsersListPage.java
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

package com.auth0.management.result;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class UsersListPage {

    @SerializedName("start")
    private Integer start;

    @SerializedName("limit")
    private Integer limit;

    @SerializedName("length")
    private Integer length;

    @SerializedName("total")
    private Integer total;

    @SerializedName("users")
    private List<User> users;

    protected UsersListPage() {

    }

    @SuppressWarnings("unchecked")
    public UsersListPage(Map<String, Object> values) {
        this.start = (Integer) values.remove("start");
        this.limit = (Integer) values.remove("limit");
        this.length = (Integer) values.remove("length");
        this.total = (Integer) values.remove("total");
        this.users = buildUsers((List<Map<String, Object>>) values.remove("users"));
    }

    public Integer getStart() {
        return start;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getTotal() {
        return total;
    }

    public List<User> getUsers() {
        return users;
    }

    private List<User> buildUsers(List<Map<String, Object>> values) {
        if (values == null) {
            return Collections.emptyList();
        }
        List<User> users = new ArrayList<>(values.size());
        for (Map<String, Object> value : values) {
            users.add(new User(value));
        }
        return users;
    }

    @Override
    public String toString() {
        return "UsersListPage{" +
                "start=" + start +
                ", limit=" + limit +
                ", length=" + length +
                ", total=" + total +
                ", users=" + users +
                '}';
    }
}
