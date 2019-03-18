package io.github.frapples.javademoandcookbook.demoandcase.cookbook.http;

import com.alibaba.fastjson.JSONObject;
import io.github.frapples.javademoandcookbook.commonutils.utils.serialization.JsonUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/18
 *
 * 使用库：apache http components client fluent风格
 * 用于测试的HTTP API： postman提供的echo API, https://docs.postman-echo.com/#
 * 更多参见文档：https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fluent.html
 */
public class HttpClientCookbook {

    void get() throws IOException {
        String jsonString = Request.Get("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
            .connectTimeout(5000)
            .socketTimeout(5000)
            .execute().returnContent().asString();
        String beautifyJsonString = JsonUtils.beautifyJsonString(jsonString);
        System.out.println(beautifyJsonString);
    }

    void postJson() throws IOException {
        String postJson = new JSONObject()
            .fluentPut("a", 1)
            .fluentPut("b", 2)
            .toJSONString();
        String jsonString = Request.Post("https://postman-echo.com/post")
            .bodyString(postJson, ContentType.APPLICATION_JSON)
            .execute().returnContent().asString();
        String beautifyJsonString = JsonUtils.beautifyJsonString(jsonString);
        System.out.println(beautifyJsonString);
    }

    void postFile() throws IOException {
        String jsonString = Request.Post("https://postman-echo.com/post")
            .body(MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("file", new ByteArrayInputStream("abcdefg".getBytes()), ContentType.MULTIPART_FORM_DATA, "file.txt")
                .addTextBody("a", "1")
                .build())
            .execute().returnContent().asString();
        String beautifyJsonString = JsonUtils.beautifyJsonString(jsonString);
        System.out.println(beautifyJsonString);
    }

    void postForm() throws IOException {
        String jsonString = Request.Post("https://postman-echo.com/post")
            .addHeader("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.getMimeType())
            .bodyForm(
                new BasicNameValuePair("a", "1"),
                new BasicNameValuePair("b", "2"))
            .execute().returnContent().asString();
        String beautifyJsonString = JsonUtils.beautifyJsonString(jsonString);
        System.out.println(beautifyJsonString);
    }

    public static void main(String[] args) throws IOException {
        HttpClientCookbook c = new HttpClientCookbook();
        c.get();
        c.postJson();
        c.postFile();
        c.postForm();
    }

}
