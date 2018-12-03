package io.loadkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 资源的一个通用实现
 *
 * @author Payne 646742615@qq.com
 * 2018/12/2 10:06
 */
public class Res implements Resource {
    private final String name;
    private final URL url;

    public Res(String name, URL url) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (url == null) {
            throw new IllegalArgumentException("url must not be null");
        }
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

    public InputStream getInputStream() throws IOException {
        return url.openStream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Res that = (Res) o;

        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return url.toString();
    }
}
