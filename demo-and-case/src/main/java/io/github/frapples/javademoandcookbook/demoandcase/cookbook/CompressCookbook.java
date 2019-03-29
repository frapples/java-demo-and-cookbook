package io.github.frapples.javademoandcookbook.demoandcase.cookbook;

import io.github.frapples.javademoandcookbook.commonutils.utils.stream.DoNotCloseMeInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.IOUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/29
 */
public class CompressCookbook {

    public static void compressZip(OutputStream outputStream) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(outputStream);
        // 设置压缩算法
        zipOut.setMethod(ZipOutputStream.DEFLATED);
        // 设置压缩等级，共0-9级，0为不压缩
        zipOut.setLevel(ZipOutputStream.STORED);
        try {
            zipOut.putNextEntry(new ZipEntry("a.txt"));
            IOUtils.copy(new ByteArrayInputStream("abc".getBytes()), zipOut);
            zipOut.putNextEntry(new ZipEntry("b.txt"));
            IOUtils.copy(new ByteArrayInputStream("def".getBytes()), zipOut);
        } finally {
            zipOut.finish();
            zipOut.flush();
        }
    }

    public static void uncompressZip(InputStream inputStream) throws IOException {
        try (ZipInputStream stream = new ZipInputStream(new DoNotCloseMeInputStream(inputStream))) {
            ZipEntry entry;
            while((entry = stream.getNextEntry()) != null) {
                byte[] bytes = IOUtils.toByteArray(stream);
            }
        }
    }
}
