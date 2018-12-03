package io.loadkit;

import java.util.Collection;

/**
 * 过滤器工具类
 *
 * @author Payne 646742615@qq.com
 * 2018/12/3 10:56
 */
public abstract class Filters {

    public static Filter all(Filter... filters) {
        return new AllFilter(filters);
    }

    public static Filter all(Collection<? extends Filter> filters) {
        return new AllFilter(filters);
    }

    public static Filter and(Filter... filters) {
        return all(filters);
    }

    public static Filter and(Collection<? extends Filter> filters) {
        return all(filters);
    }

    public static Filter any(Filter... filters) {
        return new AnyFilter(filters);
    }

    public static Filter any(Collection<? extends Filter> filters) {
        return new AnyFilter(filters);
    }

    public static Filter or(Filter... filters) {
        return any(filters);
    }

    public static Filter or(Collection<? extends Filter> filters) {
        return any(filters);
    }

}
