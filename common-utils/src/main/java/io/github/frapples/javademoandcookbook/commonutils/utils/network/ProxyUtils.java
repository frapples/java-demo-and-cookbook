package io.github.frapples.javademoandcookbook.commonutils.utils.network;

import com.google.common.collect.ImmutableMap;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/4/29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProxyUtils {

    private final static Pattern PATTERN = Pattern.compile("(http://|https://|socket://)([^:/]+)(:|)([0-9]+)(/|)");

    /**
     * @param proxyString 代理设置字符串 格式如: http://127.0.0.1:8888
     * @return Proxy对象
     */
    public static Proxy fromString(String proxyString) {
        if (StringUtils.isEmpty(proxyString)) {
            return null;
        }
        Matcher m = PATTERN.matcher(proxyString);
        // 必须调用matches()，我服了
        if (!m.matches() || m.groupCount() < 4) {
            throw new IllegalArgumentException(String.format("Invalid format: %s", proxyString));
        }

        String host = m.group(2);
        int port = Integer.parseInt(m.group(4));

        Type type = ImmutableMap.of(
            "socket://", Type.SOCKS,
            "http://", Type.HTTP,
            "https://", Type.HTTP)
            .get(m.group(1).toLowerCase());
        if (type == null) {
            throw new IllegalArgumentException(String.format("Invalid format: %s", proxyString));
        }

        return new Proxy(type, new InetSocketAddress(host, port));
    }
}
