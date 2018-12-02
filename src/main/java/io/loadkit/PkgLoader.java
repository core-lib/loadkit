package io.loadkit;

import java.io.IOException;
import java.util.Enumeration;

/**
 * 包名表达式资源加载器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/2 13:31
 */
public class PkgLoader extends DelegateLoader implements Loader {

    public PkgLoader() {
        this(new StdLoader());
    }

    public PkgLoader(ClassLoader classLoader) {
        this(new StdLoader(classLoader));
    }

    public PkgLoader(Loader delegate) {
        super(delegate);
    }

    public Enumeration<Resource> load(String pkg, boolean recursively, Filter filter) throws IOException {
        String path = pkg.replace('.', '/');
        return delegate.load(path, recursively, filter);
    }
}
