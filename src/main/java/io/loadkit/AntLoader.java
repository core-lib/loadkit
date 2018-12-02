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

    public AntLoader(Loader delegate) {
        super(delegate);
    }

    public static void main(String... args) throws IOException {
        Loader loader = new AntLoader(new ProjLoader());
        Enumeration<Resource> enumeration = loader.load("*", true);
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement().getName());
        }
    }

    protected String path(String ant) {
        int index = Math.min(ant.indexOf('*'), ant.indexOf('?'));
        return ant.substring(0, ant.lastIndexOf('/', index) + 1);
    }

    protected boolean recursively(String ant) {
        return true;
    }

    protected Filter filter(String ant) {
        return new AntFilter(ant);
    }
}
