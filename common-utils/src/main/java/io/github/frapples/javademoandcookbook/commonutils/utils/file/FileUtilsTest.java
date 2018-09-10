package io.github.frapples.javademoandcookbook.commonutils.utils.file;

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
