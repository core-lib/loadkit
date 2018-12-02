package io.loadkit;

import java.io.IOException;
import java.util.Enumeration;

/**
 * ANT风格路径资源加载器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/2 10:59
 */
public class AntLoader extends PatternLoader implements Loader {

    public AntLoader() {
        this(new StdLoader());
    }

    public AntLoader(ClassLoader classLoader) {
        this(new StdLoader(classLoader));
    }

    public AntLoader(Loader delegate) {
        super(delegate);
    }

    @Override
    public Enumeration<Resource> load(String pattern, boolean recursively, Filter filter) throws IOException {
        if (Math.max(pattern.indexOf('*'), pattern.indexOf('?')) < 0) {
            return delegate.load(pattern, recursively, filter);
        } else {
            return super.load(pattern, recursively, filter);
        }
    }

    protected String path(String ant) {
        int index = Integer.MAX_VALUE - 1;
        if (ant.contains("*") && ant.indexOf('*') < index) index = ant.indexOf('*');
        if (ant.contains("?") && ant.indexOf('?') < index) index = ant.indexOf('?');
        return ant.substring(0, ant.lastIndexOf('/', index) + 1);
    }

    protected boolean recursively(String ant) {
        return true;
    }

    protected Filter filter(String ant) {
        return new AntFilter(ant);
    }
}
