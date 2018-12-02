package io.loadkit;

/**
 * 资源过滤器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/1 17:02
 */
public interface Filter {

    Filter ALL = new Filter() {
        public boolean filtrate(Resource resource) {
            return true;
        }
    };

    /**
     * 过滤资源
     *
     * @param resource 资源
     * @return true: 加载  false: 不加载
     */
    boolean filtrate(Resource resource);

}
