package io.github.frapples.javademoandcookbook.commonutils.utils.secure;


import com.google.common.base.Preconditions;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.IOUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @see BaseEncoding Guava的此工具类中提供base64编码解码实现
 *
 */
public class SecureUtils {

    static public String randomSalt() {
        return randomString(32);
    }

    /**
     3. SecureRandom可用于生成安全的随机字符串
     * @param length 长度
     * @return 随机字符串
     **/
    static public String randomString(int length) {
        final Random r = new SecureRandom();
        byte[] salt = new byte[length];
        r.nextBytes(salt);
        String saltStr = BaseEncoding.base64().encode(salt);
        return saltStr.substring(0, length);
    }

    static public String uuidAs32Bytes() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");

        Preconditions.checkArgument(uuid.length() == 32);

        return uuid;
    }

    /**
     1. Guava的Hashing库提供了一组对hash函数的实现
     * @param password 密码
     * @param salt 盐值
     * @return hash值
     **/
    static public String hashPassword(String password, String salt) {
        return Hashing.sha256().hashString(password + salt,
            StandardCharsets.UTF_8).toString();
    }


    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    static public String hash(HashFunction hashFunction, InputStream input) throws IOException {
        Hasher hasher = hashFunction.newHasher();
        int n;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while (IOUtils.EOF != (n = input.read(buffer))) {
            hasher.putBytes(buffer, 0, n);
        }
        return hasher.hash().toString();
    }

    /**
     * 简单的对zip文件进行hash，解开zip包对原始文件和文件名进行hash。
     * 此hash算法非标准，仅供测试时使用。
     * @param hashFunction hash函数
     * @see com.google.common.hash.Hashing
     * @param inputStream 格式为zip的输入流
     * @return hash值
     * @throws IOException
     */
    public static String hashZipForTest(HashFunction hashFunction, InputStream inputStream) throws IOException {
        ZipInputStream stream = new ZipInputStream(inputStream);
        ZipEntry entry;
        Hasher hasher = hashFunction.newHasher();
        while((entry = stream.getNextEntry()) != null) {
            hasher.putBytes(entry.getName().getBytes());
            entry.getName();
            int n;
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            while (IOUtils.EOF != (n = stream.read(buffer))) {
                hasher.putBytes(buffer, 0, n);
            }
        }
        return hasher.hash().toString();
    }
}
