package io.loadkit;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * 标准的资源加载器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/1 23:10
 */
public class StdLoader extends ResourceLoader implements Loader {
    private final ClassLoader classLoader;

    public StdLoader() {
        this(Thread.currentThread().getContextClassLoader() != null ? Thread.currentThread().getContextClassLoader() : ClassLoader.getSystemClassLoader());
    }

    public StdLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            throw new IllegalArgumentException("classLoader must not be null");
        }
        this.classLoader = classLoader;
    }

    public Enumeration<Resource> load(String path, boolean recursively, Filter filter) throws IOException {
        while (path.startsWith("/")) path = path.substring(1);
        while (path.endsWith("/")) path = path.substring(0, path.length() - 1);
        return new Enumerator(classLoader, path, recursively, filter != null ? filter : Filters.ALWAYS);
    }

    private static class Enumerator extends ResourceEnumerator implements Enumeration<Resource> {
        private final String path;
        private final boolean recursively;
        private final Filter filter;
        private final Enumeration<URL> urls;
        private Enumeration<Resource> resources;

        Enumerator(ClassLoader classLoader, String path, boolean recursively, Filter filter) throws IOException {
            this.path = path;
            this.recursively = recursively;
            this.filter = filter;
            this.urls = load(classLoader, path);
            this.resources = Collections.enumeration(Collections.<Resource>emptySet());
        }

        private Enumeration<URL> load(ClassLoader classLoader, String path) throws IOException {
            if (path.length() > 0) {
                return classLoader.getResources(path);
            } else {
                Set<URL> set = new LinkedHashSet<URL>();
                set.add(classLoader.getResource(path));
                Enumeration<URL> urls = classLoader.getResources("META-INF/");
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    if (url.getProtocol().equalsIgnoreCase("jar")) {
                        String spec = url.toString();
                        int index = spec.lastIndexOf("!/");
                        if (index < 0) continue;
                        set.add(new URL(url, spec.substring(0, index + "!/".length())));
                    }
                }
                return Collections.enumeration(set);
            }
        }

        public boolean hasMoreElements() {
            if (next != null) {
                return true;
            } else if (!resources.hasMoreElements() && !urls.hasMoreElements()) {
                return false;
            } else if (resources.hasMoreElements()) {
                next = resources.nextElement();
                return true;
            } else {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                if ("file".equalsIgnoreCase(protocol)) {
                    try {
                        String uri = Uris.decode(url.getPath(), Charset.defaultCharset());
                        String root = uri.substring(0, uri.lastIndexOf(path));
                        URL context = new URL(url, "file:" + Uris.encodePath(root, Charset.defaultCharset()));
                        File file = new File(root);
                        resources = new FileLoader(context, file).load(path, recursively, filter);
                        return hasMoreElements();
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                } else if ("jar".equalsIgnoreCase(protocol)) {
                    try {
                        String uri = Uris.decode(url.getPath(), Charset.defaultCharset());
                        String root = uri.substring(0, uri.lastIndexOf(path));
                        URL context = new URL(url, "jar:" + Uris.encodePath(root, Charset.defaultCharset()));
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        resources = new JarLoader(context, jarFile).load(path, recursively, filter);
                        return hasMoreElements();
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                } else {
                    return hasMoreElements();
                }
            }
        }
    }

}
