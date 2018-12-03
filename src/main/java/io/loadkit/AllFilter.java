package io.loadkit;

import java.net.URL;
import java.util.Collection;

/**
 * ALL逻辑复合过滤器，即所有过滤器都满足的时候才满足，只要有一个过滤器不满足就立刻返回不满足，如果没有过滤器的时候则认为所有过滤器都满足。
 *
 * @author Payne 646742615@qq.com
 * 2018/12/3 10:28
 */
public class AllFilter extends MixFilter implements Filter {

    public AllFilter(Filter... filters) {
        super(filters);
    }

    public AllFilter(Collection<? extends Filter> filters) {
        super(filters);
    }

    public boolean filtrate(String name, URL url) {
        Filter[] filters = this.filters.toArray(new Filter[0]);
        for (Filter filter : filters) {
            if (!filter.filtrate(name, url)) {
                return false;
            }
        }
        return true;
    }

    public AllFilter mix(Filter filter) {
        add(filter);
        return this;
    }

}
