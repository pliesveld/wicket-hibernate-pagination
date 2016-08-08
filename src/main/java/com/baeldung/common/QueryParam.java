package com.baeldung.common;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;

public class QueryParam {
    private long first;
    private long count;
    private SortParam<String> sort;

    public QueryParam(long first, long count, SortParam<String> sort) {
        this.first = first;
        this.count = count;
        this.sort = sort;
    }

    public QueryParam(long first, long count) {
        this(first, count, null);
    }

    public long getFirst() {
        return first;
    }

    public long getCount() {
        return count;
    }

    /**
     * @return The property value of {@link SortParam}
     * to sort the query result by.  Defaults to {@literal "Id"}.
     */
    public String getProperty() {
        return sort != null ? sort.getProperty() : null;
    }

    public boolean isAscending() {
        return sort != null ? sort.isAscending() : true;
    }
}
