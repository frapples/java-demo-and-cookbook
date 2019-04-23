package io.github.frapples.javademoandcookbook.commonutils.utils.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.frapples.javademoandcookbook.commonutils.utils.serialization.JsonUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

/**
 * https://www.jianshu.com/p/b2057bcf512d
 * 1. 演示如何使用ApacheHttpClient发送请求
 * 2. 将内存中的字符串向外传输时, 要特别注意编码问题. 最好的方式是手动控制转为bytes
 * @author Frapples
 **/
@Slf4j
public class HttpClientUtils {


    enum Method {
        POST_FORM,
        POST_JSON,
        POST_FILE,
        GET,
    }

    @AllArgsConstructor
    @Getter
    public static class FileEntity {
        String fileName;
        InputStream inputStream;
    }

    public static String http(@NonNull Method method, @NonNull String url, Map<String, Object> param, Map<String, String> headers)
        throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpUriRequest httpRequest;
            if (Method.GET.equals(method)) {
                String newUrl = urlEncode(url, param);
                httpRequest = new HttpGet(newUrl);
            } else if (Method.POST_FORM.equals(method)) {
                HttpPost httpPost = new HttpPost(url);
                List<NameValuePair> form = new ArrayList<>();
                param.forEach((k, v) -> form.add(new BasicNameValuePair(k, String.valueOf(v))));
                httpPost.setEntity(new UrlEncodedFormEntity(form));
                httpRequest = httpPost;
            } else if (Method.POST_JSON.equals(method)) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new StringEntity(JSON.toJSONString(param)));
                httpRequest = httpPost;

                httpRequest.setHeader("Charset", "UTF-8");
                httpRequest.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
            } else if (Method.POST_FILE.equals(method)) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

                param.forEach((k, v) -> {
                    if (v instanceof FileEntity) {
                        FileEntity fileEntity = (FileEntity) v;
                        builder.addBinaryBody(k, fileEntity.inputStream, ContentType.create("multipart/form-data"), fileEntity.fileName);
                    } else {
                        builder.addTextBody(k, String.valueOf(v));
                    }
                });
                HttpEntity httpEntity = builder.build();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(httpEntity);
                httpRequest = httpPost;

            } else {
                throw new IllegalArgumentException();
            }

            headers.forEach(httpRequest::setHeader);

            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                HttpEntity entity = response.getEntity();
                return IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
            }
        }
    }

    @SneakyThrows
    public static String urlEncode(String url, Map<String, Object> map) {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        map.forEach((key, value) -> pairs.add(new BasicNameValuePair(key, String.valueOf(value))));
        return new URIBuilder(url).addParameters(pairs).toString();
    }

    public static String urlEncode(Map<String, Object> map) {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        map.forEach((key, value) -> pairs.add(new BasicNameValuePair(key, String.valueOf(value))));
        return URLEncodedUtils.format(pairs, StandardCharsets.UTF_8);
    }

    public static String post(String url, Map<String, String> para) throws IOException {
        List<NameValuePair> form = new ArrayList<>();
        para.forEach((k, v) -> {
            form.add(new BasicNameValuePair(k, v));
        });

        return post(url, new UrlEncodedFormEntity(form, "UTF-8"), new HashMap<>());
    }

    public static String postJson(String url, Map<String, Object> para) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Charset", "UTF-8");
        headers.put("Content-Type", "application/json");

        // HttpEntity entity = new StringEntity(JSON.toJSONString(para); //  Buggly
        HttpEntity entity = new ByteArrayEntity(JSON.toJSONString(para).getBytes(StandardCharsets.UTF_8));
        return post(url, entity, headers);
    }

    public static String post(String url, HttpEntity httpEntity, Map<String, String> headers) throws IOException {
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

    public static String get(String url) throws IOException {
        return get(url, Collections.emptyMap());
    }

    public static String get(String url, Map<String, String> headers) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);

            headers.forEach(httpGet::setHeader);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                return IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
            }
        }
    }

    public static JSONObject postFileAsJson(String url,
        String fileField, String fileName, InputStream inputStream,
        Map<String, String> data) throws IOException {
        log.info("准备上传文件，文件名：{}", fileName);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody(fileField, inputStream, ContentType.create("multipart/form-data"), fileName);

            for (Map.Entry<String, String> p : data.entrySet()) {
                builder.addTextBody(p.getKey(), p.getValue());
            }
            HttpEntity httpEntity = builder.build();

            //发送请求
            HttpPost post = new HttpPost(url);
            post.setEntity(httpEntity);
            HttpResponse response = client.execute(post);

            HttpEntity entity = response.getEntity();
            return JSONObject.parseObject(entity.getContent(), JSONObject.class);
        }

    }

}
