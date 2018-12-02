package io.loadkit;

import java.io.IOException;
import java.util.Enumeration;

/**
 * 资源加载器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/1 17:02
 */
public interface Loader {

    /**
     * 加载指定路径的一个资源，有可能存在多个满足条件的资源，但只返回第一个。
     *
     * @param path 资源路径
     * @return 资源对象
     * @throws IOException I/O 异常
     */
    Resource load(String path) throws IOException;

    /**
     * 加载指定路径的所有资源。
     *
     * @param path        资源路径
     * @param recursively 递归加载
     * @return 资源枚举器
     * @throws IOException I/O 异常
     */
    Enumeration<Resource> load(String path, boolean recursively) throws IOException;

    /**
     * 加载指定路径的所有满足过滤条件的资源，等效于Loader.load(path, true, filter)的调用。
     *
     * @param path   资源路径
     * @param filter 过滤器
     * @return 资源枚举器
     * @throws IOException I/O 异常
     */
    Enumeration<Resource> load(String path, Filter filter) throws IOException;

    /**
     * 加载指定路径的所有满足过滤条件的资源。
     *
     * @param path        资源路径
     * @param recursively 递归加载
     * @param filter      过滤器
     * @return 资源枚举器
     * @throws IOException I/O 异常
     */
    Enumeration<Resource> load(String path, boolean recursively, Filter filter) throws IOException;

}
