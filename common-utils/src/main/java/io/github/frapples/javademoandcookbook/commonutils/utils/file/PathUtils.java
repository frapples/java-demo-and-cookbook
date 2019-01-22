package io.github.frapples.javademoandcookbook.commonutils.utils.file;

import java.util.Arrays;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 */
public class PathUtils {

    public static final char SEP = System.getProperty("file.separator").charAt(0);
    private static final List<Character> allSep = Arrays.asList('/', '\\', SEP);


    static String pathJoin(String... seg) {
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
                path.append(SEP);
            }
        }
        return path.toString();
    }
}
