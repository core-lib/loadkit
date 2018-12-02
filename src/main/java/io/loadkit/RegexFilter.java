package io.loadkit;

import java.net.URL;
import java.util.regex.Pattern;

/**
 * 正则表达式过滤器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/2 11:41
 */
public class RegexFilter implements Filter {
    private final Pattern pattern;

    public RegexFilter(String regex) {
        this(Pattern.compile(regex));
    }

    public RegexFilter(Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern must not be null");
        }
        this.pattern = pattern;
    }

    public boolean filtrate(String name, URL url) {
        return pattern.matcher(name).matches();
    }
}
