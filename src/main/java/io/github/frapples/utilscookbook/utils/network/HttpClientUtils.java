package io.github.frapples.utilscookbook.utils.network;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 * https://www.jianshu.com/p/b2057bcf512d
 * 1. 演示如何使用ApacheHttpClient发送请求
 * 2. 将内存中的字符串向外传输时, 要特别注意编码问题. 最好的方式是手动控制转为bytes
 * @author Frapples
 **/
public class HttpClientUtils {

    public String post(String url, Map<String, String> para) throws IOException {
        List<NameValuePair> form = new ArrayList<>();
        para.forEach((k, v) -> {
            form.add(new BasicNameValuePair(k, v));
        });

        return post(url, new UrlEncodedFormEntity(form, "UTF-8"), new HashMap<>());
    }

    public String postJson(String url, Map<String, Object> para) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Charset", "UTF-8");
        headers.put("Content-Type", "application/json");

        // HttpEntity entity = new StringEntity(JSON.toJSONString(para); //  Buggly
        HttpEntity entity = new ByteArrayEntity(JSON.toJSONString(para).getBytes(StandardCharsets.UTF_8));
        return post(url, entity, headers);
    }

    public String post(String url, HttpEntity httpEntity, Map<String, String> headers) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            headers.forEach(httpPost::setHeader);
            httpPost.setEntity(httpEntity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                String resultText = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
                return resultText;
            }
        }
    }
}
