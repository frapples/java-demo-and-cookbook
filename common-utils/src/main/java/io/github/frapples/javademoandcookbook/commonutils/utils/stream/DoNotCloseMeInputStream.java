package io.github.frapples.javademoandcookbook.commonutils.utils.stream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/29
 *
 * InputStream装饰器，close()被设置为什么也不做。
 * 适用场景：某些函数画蛇添足地关闭流
 */
public class DoNotCloseMeInputStream extends InputStream {

    private InputStream inputStream;

    public DoNotCloseMeInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public boolean markSupported() {
        return this.inputStream.markSupported();
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public int available() throws IOException {
        return this.inputStream.available();
    }

    @Override
    public void mark(int readlimit) {
        this.inputStream.mark(readlimit);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.inputStream.read(b, off, len);
    }

    @Override
    public int read() throws IOException {
        return this.inputStream.read();
    }

    @Override
    public void reset() throws IOException {
        this.inputStream.reset();
    }

    @Override
    public long skip(long n) throws IOException {
        return this.inputStream.skip(n);
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.inputStream.read(b);
    }
}
