package io.github.frapples.utilscookbook.utils.file;

import io.github.frapples.utilscookbook.utils.file.FileUtils;
import java.io.IOException;

public class FileUtilsTest {

    public static void main(String[] args) {
        try {
            System.out.println(FileUtils.readCalssPathFile("files/customerFile.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
