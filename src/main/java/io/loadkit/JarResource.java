package io.loadkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.jar.JarEntry;

/**
 * Jar 资源
 *
 * @author Payne 646742615@qq.com
 * 2018/12/1 18:05
 */
public class JarResource implements Resource {
    private final String name;
    private final URL url;

    JarResource(URL context, JarEntry jarEntry) {
        try {
            this.name = jarEntry.getName();
            this.url = new URL(context, UriKit.encodePath(name, Charset.defaultCharset()));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
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

        JarResource that = (JarResource) o;

        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public String toString() {
        return url.toString();
    }
}
