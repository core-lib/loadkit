package io.loadkit;

import java.net.URL;

/**
 * 资源过滤器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/1 17:02
 */
public interface Filter {

    /**
     * 过滤资源
     *
     * @param name 资源名称，即相对路径
     * @param url  资源URL地址
     * @return true: 加载  false: 不加载
     */
    boolean filtrate(String name, URL url);

}
