package io.loadkit;

import java.io.IOException;
import java.util.Enumeration;

/**
 * 正则表达式资源加载器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/2 12:31
 */
public class RegexLoader extends PatternLoader implements Loader {

    public RegexLoader(Loader delegate) {
        super(delegate);
    }

    public static void main(String... args) throws IOException {
        Loader loader = new RegexLoader(new ProjLoader());
        Enumeration<Resource> enumeration = loader.load("io/loadkit/\\w+\\.class", true);
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement().getName());
        }
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
