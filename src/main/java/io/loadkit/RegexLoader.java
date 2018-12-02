package io.loadkit;

/**
 * 正则表达式资源加载器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/2 12:31
 */
public class RegexLoader extends PatternLoader implements Loader {

    public RegexLoader() {
        this(new StdLoader());
    }

    public RegexLoader(ClassLoader classLoader) {
        this(new StdLoader(classLoader));
    }

    public RegexLoader(Loader delegate) {
        super(delegate);
    }

    protected String path(String pattern) {
        return "";
    }

    protected boolean recursively(String pattern) {
        return true;
    }

    protected Filter filter(String pattern) {
        return new RegexFilter(pattern);
    }
}
