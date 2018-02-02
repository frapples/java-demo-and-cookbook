package io.github.frapples.utilscookbook.utils;


import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class SecureUtils {

    static public String randomSalt() {
        return randomString(32);
    }

    /*
    1. Guava的BaseEncoding提供了一些列base64的实现
    2. 更多Base64实现：http://www.importnew.com/14961.html
    3. SecureRandom可用于生成安全的随机字符串
    * */
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

    /*
    1. Guava的Hashing库提供了一组对hash函数的实现
    * */
    static public String hashPassword(String password, String salt) {
        return Hashing.sha256().hashString(password + salt,
            StandardCharsets.UTF_8).toString();
    }
}
