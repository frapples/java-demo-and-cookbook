package io.github.frapples.javademoandcookbook.commonutils.utils.file;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 *
 * @see org.apache.commons.io.FilenameUtils 提供了文件路径操作的工具。
 */
public class PathUtils {

    public static final char SEP = System.getProperty("file.separator").charAt(0);
    private static final List<Character> allSep = Arrays.asList('/', '\\', SEP);


    public static String join(String... seg) {
        if (seg == null || seg.length == 0) {
            return "";
        }

        StringBuilder path = new StringBuilder();
        for (int i = 0; i < seg.length; i++) {
            String s = seg[i];
            if (s.length() > 0) {
                int first = 0;
                int end = s.length() - 1;
                if (allSep.contains(s.charAt(first))) {
                    first++;
                }
                if (allSep.contains(s.charAt(end))) {
                    end--;
                }
                path.append(s, first, end + 1);
                if (i != seg.length - 1) {
                    path.append(SEP);
                }
            }
        }
        return path.toString();
    }

    public static String dirname(String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }

        if (allSep.contains(path.charAt(path.length() - 1))) {
            path = path.substring(0, path.length() - 1);
        }
        return FilenameUtils.getFullPath(path);
    }

    public static String basename(String path) {
        return FilenameUtils.getName(path);
    }
}
