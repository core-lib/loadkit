package io.loadkit;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Jar包资源加载器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/1 17:28
 */
public class JarLoader extends ResourceLoader implements Loader {
    private final URL context;
    private final JarFile jarFile;

    public JarLoader(File file) throws IOException {
        this(new URL("jar:" + file.toURI().toURL() + "!/"), new JarFile(file));
    }

    public JarLoader(URL jarURL) throws IOException {
        this(jarURL, ((JarURLConnection) jarURL.openConnection()).getJarFile());
    }

    public JarLoader(URL context, JarFile jarFile) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        if (jarFile == null) {
            throw new IllegalArgumentException("jarFile must not be null");
        }
        this.context = context;
        this.jarFile = jarFile;
    }

    public Enumeration<Resource> load(String path, boolean recursively, Filter filter) {
        while (path.startsWith("/")) path = path.substring(1);
        while (path.endsWith("/")) path = path.substring(0, path.length() - 1);
        return new Enumerator(context, jarFile, path, recursively, filter != null ? filter : Filters.ALWAYS);
    }

    private static class Enumerator extends ResourceEnumerator implements Enumeration<Resource> {
        private final URL context;
        private final String path;
        private final String folder;
        private final boolean recursively;
        private final Filter filter;
        private final Enumeration<JarEntry> entries;

        Enumerator(URL context, JarFile jarFile, String path, boolean recursively, Filter filter) {
            this.context = context;
            this.path = path;
            this.folder = path.endsWith("/") || path.length() == 0 ? path : path + "/";
            this.recursively = recursively;
            this.filter = filter;
            this.entries = jarFile.entries();
        }

        public boolean hasMoreElements() {
            if (next != null) {
                return true;
            }
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.isDirectory()) {
                    continue;
                }
                String name = jarEntry.getName();
                if (name.equals(path)
                        || (recursively && name.startsWith(folder))
                        || (!recursively && name.startsWith(folder) && name.indexOf('/', folder.length()) < 0)) {
                    try {
                        URL url = new URL(context, Uris.encodePath(name, Charset.defaultCharset()));
                        if (filter.filtrate(name, url)) {
                            next = new Res(name, url);
                            return true;
                        }
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
            return false;
        }

    }

}
