package io.github.frapples.javademoandcookbook.commonutils.utils.file;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/10
 */

@Slf4j
class FileUtilsTest {

    @Test
    void readCalssPathFile() {

        try {
            String context = FileUtils.readCalssPathFile("files/customerFile.txt");
            log.info(context);
        } catch (IOException e) {
            log.info("", e);
        }
    }
}
