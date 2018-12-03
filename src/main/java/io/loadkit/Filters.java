package io.loadkit;

import java.net.URL;
import java.util.Collection;

/**
 * 过滤器工具类
 *
 * @author Payne 646742615@qq.com
 * 2018/12/3 10:56
 */
public abstract class Filters {

    /**
     * 永远返回true的过滤器
     */
    public static final Filter ALWAYS = new Filter() {
        public boolean filtrate(String name, URL url) {
            return true;
        }
    };

    /**
     * 永远返回false的过滤器
     */
    public static final Filter NEVER = new Filter() {
        public boolean filtrate(String name, URL url) {
            return false;
        }
    };

    /**
     * 创建多个子过滤器AND连接的混合过滤器
     *
     * @param filters 子过滤器
     * @return 多个子过滤器AND连接的混合过滤器
     */
    public static Filter all(Filter... filters) {
        return new AllFilter(filters);
    }

    /**
     * 创建多个子过滤器AND连接的混合过滤器
     *
     * @param filters 子过滤器
     * @return 多个子过滤器AND连接的混合过滤器
     */
    public static Filter all(Collection<? extends Filter> filters) {
        return new AllFilter(filters);
    }

    /**
     * 创建多个子过滤器AND连接的混合过滤器
     *
     * @param filters 子过滤器
     * @return 多个子过滤器AND连接的混合过滤器
     */
    public static Filter and(Filter... filters) {
        return all(filters);
    }

    /**
     * 创建多个子过滤器AND连接的混合过滤器
     *
     * @param filters 子过滤器
     * @return 多个子过滤器AND连接的混合过滤器
     */
    public static Filter and(Collection<? extends Filter> filters) {
        return all(filters);
    }

    /**
     * 创建多个子过滤器OR连接的混合过滤器
     *
     * @param filters 子过滤器
     * @return 多个子过滤器OR连接的混合过滤器
     */
    public static Filter any(Filter... filters) {
        return new AnyFilter(filters);
    }

    /**
     * 创建多个子过滤器OR连接的混合过滤器
     *
     * @param filters 子过滤器
     * @return 多个子过滤器OR连接的混合过滤器
     */
    public static Filter any(Collection<? extends Filter> filters) {
        return new AnyFilter(filters);
    }

    /**
     * 创建多个子过滤器OR连接的混合过滤器
     *
     * @param filters 子过滤器
     * @return 多个子过滤器OR连接的混合过滤器
     */
    public static Filter or(Filter... filters) {
        return any(filters);
    }

    /**
     * 创建多个子过滤器OR连接的混合过滤器
     *
     * @param filters 子过滤器
     * @return 多个子过滤器OR连接的混合过滤器
     */
    public static Filter or(Collection<? extends Filter> filters) {
        return any(filters);
    }

}
