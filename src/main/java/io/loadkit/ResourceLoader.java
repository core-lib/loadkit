package io.loadkit;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * 资源加载器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/1 17:41
 */
public abstract class ResourceLoader implements Loader {

    public Enumeration<Resource> load(String path) throws IOException {
        return load(path, false, Filter.TRUE);
    }

    public Enumeration<Resource> load(String path, boolean recursively) throws IOException {
        return load(path, recursively, Filter.TRUE);
    }

    public Enumeration<Resource> load(String path, Filter filter) throws IOException {
        return load(path, true, filter);
    }

    /**
     * 资源枚举器
     *
     * @author Payne 646742615@qq.com
     * 2018/12/1 22:43
     */
    protected abstract static class ResourceEnumerator implements Enumeration<Resource> {
        protected Resource next;

        public Resource nextElement() {
            if (hasMoreElements()) {
                Resource resource = next;
                next = null;
                return resource;
            } else {
                throw new NoSuchElementException();
            }
        }

    }
}
