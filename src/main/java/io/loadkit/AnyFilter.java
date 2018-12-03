package io.loadkit;

import java.net.URL;
import java.util.Collection;

/**
 * ANY逻辑复合过滤器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/3 10:47
 */
public class AnyFilter extends MixFilter implements Filter {

    public AnyFilter(Filter... filters) {
        super(filters);
    }

    public AnyFilter(Collection<? extends Filter> filters) {
        super(filters);
    }

    public boolean filtrate(String name, URL url) {
        Filter[] filters = this.filters.toArray(new Filter[0]);
        for (Filter filter : filters) {
            if (filter.filtrate(name, url)) {
                return true;
            }
        }
        return false;
    }

    public AnyFilter mix(Filter filter) {
        add(filter);
        return this;
    }
}
