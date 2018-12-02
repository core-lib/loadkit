package io.loadkit;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * 混合过滤器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/2 11:29
 */
public class MixedFilter implements Filter {
    private final Collection<Filter> filters;

    public MixedFilter(Filter... filters) {
        this(Arrays.asList(filters));
    }

    public MixedFilter(Collection<? extends Filter> filters) {
        this.filters = filters != null ? Collections.unmodifiableCollection(filters) : Collections.<Filter>emptySet();
    }

    public boolean filtrate(String name, URL url) {
        for (Filter filter : filters) {
            if (!filter.filtrate(name, url)) {
                return false;
            }
        }
        return true;
    }
}
