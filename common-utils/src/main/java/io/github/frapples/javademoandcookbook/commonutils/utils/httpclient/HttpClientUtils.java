package io.github.frapples.javademoandcookbook.commonutils.utils.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/1/8
 */
public class HttpClientUtils {

    public static InputStream uploadFile(String url, String fileFieldName, InputStream inputStream, String fileName, Map<String, String> data)
        throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("image", inputStream, ContentType.create("multipart/form-data"), fileName);
            data.forEach(builder::addTextBody);
            HttpEntity httpEntity = builder.build();

            //发送请求
            HttpPost post = new HttpPost(url);
            post.setEntity(httpEntity);
            HttpResponse response = client.execute(post);

            HttpEntity entity = response.getEntity();
            return entity.getContent();
        }
    }
}
