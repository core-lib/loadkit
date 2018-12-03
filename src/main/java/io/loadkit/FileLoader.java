package io.loadkit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 文件资源加载器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/1 19:08
 */
public class FileLoader extends ResourceLoader implements Loader {
    private final URL context;
    private final File root;

    public FileLoader(File root) throws IOException {
        this(root.toURI().toURL(), root);
    }

    public FileLoader(URL fileURL) {
        this(fileURL, new File(Uris.decode(fileURL.getPath(), Charset.defaultCharset())));
    }

    public FileLoader(URL context, File root) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        if (root == null) {
            throw new IllegalArgumentException("root must not be null");
        }
        this.context = context;
        this.root = root;
    }

    public Enumeration<Resource> load(String path, boolean recursively, Filter filter) {
        return new Enumerator(context, root, path, recursively, filter != null ? filter : Filters.ALWAYS);
    }

    private static class Enumerator extends ResourceEnumerator implements Enumeration<Resource> {
        private final URL context;
        private final boolean recursively;
        private final Filter filter;
        private final Queue<File> queue;

        Enumerator(URL context, File root, String path, boolean recursively, Filter filter) {
            this.context = context;
            this.recursively = recursively;
            this.filter = filter;
            this.queue = new LinkedList<File>();
            File file = new File(root, path);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; files != null && i < files.length; i++) {
                    queue.offer(files[i]);
                }
            } else {
                queue.offer(file);
            }
        }

        public boolean hasMoreElements() {
            if (next != null) {
                return true;
            }
            while (!queue.isEmpty()) {
                File file = queue.poll();

                if (!file.exists()) {
                    continue;
                }

                if (file.isFile()) {
                    try {
                        String name = context.toURI().relativize(file.toURI()).toString();
                        URL url = new URL(context, name);
                        if (filter.filtrate(name, url)) {
                            next = new Res(name, url);
                            return true;
                        }
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }
                if (file.isDirectory() && recursively) {
                    File[] files = file.listFiles();
                    for (int i = 0; files != null && i < files.length; i++) {
                        queue.offer(files[i]);
                    }
                    return hasMoreElements();
                }
            }

            return false;
        }

    }

}
