package io.loadkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 资源对象
 *
 * @author Payne 646742615@qq.com
 * 2018/12/1 17:12
 */
public interface Resource {

    /**
     * 资源名称
     *
     * @return 资源名称
     */
    String getName();

    /**
     * 资源URL地址
     *
     * @return URL地址
     */
    URL getUrl();

    /**
     * 资源输入流
     *
     * @return 输入流
     * @throws IOException I/O 异常
     */
    InputStream getInputStream() throws IOException;

}
