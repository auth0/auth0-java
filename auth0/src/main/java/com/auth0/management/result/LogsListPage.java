/*
 * LogsListPage.java
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

import java.util.List;
import java.util.Map;


public class LogsListPage {

    @SerializedName("start")
    private Integer start;

    @SerializedName("limit")
    private Integer limit;

    @SerializedName("length")
    private Integer length;

    @SerializedName("total")
    private Integer total;

    @SerializedName("logs")
    private List<Map<String, Object>> logs;

    protected LogsListPage() {

    }

    @SuppressWarnings("unchecked")
    public LogsListPage(Map<String, Object> values) {
        this.start = (Integer) values.remove("start");
        this.limit = (Integer) values.remove("limit");
        this.length = (Integer) values.remove("length");
        this.total = (Integer) values.remove("total");
        this.logs = (List<Map<String, Object>>) values.remove("logs");
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

    public List<Map<String, Object>> getLogs() {
        return logs;
    }

    @Override
    public String toString() {
        return "LogsListPage{" +
                "start=" + start +
                ", limit=" + limit +
                ", length=" + length +
                ", total=" + total +
                ", logs=" + logs +
                '}';
    }
}
