package io.github.frapples.javademoandcookbook.commonutils.utils.file;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

/**
 * @see org.apache.commons.io.FileUtils 提供了文件操作的工具
 * @see IOUtils 提供了IO流操作的工具类
 * @
 */
public class FileUtils {

    /**
     * 1. 放在resources的资源文件会随着打包进入jar或war中，因此，存在于resources的文件不能用文件系统方式访问。
     * 2. spring提供的Resource接口提供了对各类资源的访问。其中，ClassPathResource可访问到位于resources下的资源文件。
     * 3. 当文件不存在时，会抛出FileNotFoundException。
     * 4. apache commons io库提供了一组实用工具类用于操作IO和文件。
     * @param path resources下的文件路径
     * @return 返回该文件的内容
     * @throws IOException
     */
    public static String readCalssPathFile(String path) throws IOException {
        /* 如果是完整的spring项目也可这样写。这里写会出错，原因还不清楚 */
        // UrlResource resource = new UrlResource("classpath:" + path);
        ClassPathResource resource = new ClassPathResource(path);
        String resourcePath = resource.getPath();
        // 注意不要使用resource.getFile()，此形式只支持访问存在于文件系统的文件
        URL url = resource.getURL();
        String result = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        return result;
    }
}
