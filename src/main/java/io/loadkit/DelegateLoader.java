package io.loadkit;

/**
 * 委派的资源加载器
 *
 * @author Payne 646742615@qq.com
 * 2018/12/2 11:04
 */
public abstract class DelegateLoader extends ResourceLoader implements Loader {
    protected final Loader delegate;

    protected DelegateLoader(Loader delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException("delegate must not be null");
        }
        this.delegate = delegate;
    }

}
