package io.loadkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileResource implements Resource {
    private final String name;
    private final URL url;

    FileResource(URL context, File file) {
        try {
            this.name = context.toURI().relativize(file.toURI()).toString();
            this.url = new URL(context, name);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

    public InputStream getInputStream() throws IOException {
        return url.openStream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileResource that = (FileResource) o;

        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public String toString() {
        return url.toString();
    }

}