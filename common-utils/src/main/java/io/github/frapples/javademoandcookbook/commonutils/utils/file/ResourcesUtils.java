package io.github.frapples.javademoandcookbook.commonutils.utils.file;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.util.Assert;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2020/3/18
 */
public class ResourcesUtils {

    private static final String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * {@link org.springframework.core.io.DefaultResourceLoader#getResource(String)}
     * {@link org.springframework.core.io.Resource }
     * https://stackoverflow.com/questions/6608795/what-is-the-difference-between-class-getresource-and-classloader-getresource
     * @param location
     * @return
     */
    public static URL getResource(String location) {
        Assert.notNull(location, "Location must not be null");

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if ("/".startsWith(location)) {
            return loader.getResource(location);
        }
        else if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return loader.getResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        else {
            try {
                // Try to parse the location as a URL...
                return new URL(location);
            }
            catch (MalformedURLException ex) {
                // No URL -> resolve as resource path.
                return loader.getResource(location);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getResource("classpath:keystore.jks"));
        System.out.println(getResource("file:D:\\keystore.jks"));
    }
}
