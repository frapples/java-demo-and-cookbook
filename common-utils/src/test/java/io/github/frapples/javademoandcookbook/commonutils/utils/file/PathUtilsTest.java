package io.github.frapples.javademoandcookbook.commonutils.utils.file;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 */
class PathUtilsTest {

    @Test
    void join() {
        String p1 = PathUtils.join("/tmp", "path", "to", "file.txt");
        String p2 = PathUtils.join("/tmp", "path/", "to", "file.txt");
        System.out.println(p1);
        assertEquals(p1, p2);
    }
}
