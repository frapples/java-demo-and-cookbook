package io.github.frapples.javademoandcookbook.commonutils.utils.stream;

import java.io.IOException;
import java.io.OutputStream;
import lombok.AllArgsConstructor;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/29
 *
 * OutputStream装饰器，close()被设置为什么也不做。
 * 适用场景：某些函数画蛇添足地关闭流
 */
@AllArgsConstructor
public class DoNotCloseMeOutputStream extends OutputStream {

    final private OutputStream outputStream;

    @Override
    public void close() throws IOException {
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.outputStream.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        this.outputStream.write(b);
    }

    @Override
    public void flush() throws IOException {
        this.outputStream.flush();
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.outputStream.write(b);
    }
}
