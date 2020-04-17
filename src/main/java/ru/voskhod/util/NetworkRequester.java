package ru.voskhod.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Component
public class NetworkRequester {

    private final String URL = "https://reqres.in/api/users";
    private final byte[] EASTER_EGG_IMAGE;

    public NetworkRequester() throws IOException {
        Resource resource = new ClassPathResource("images/kinda-easter-egg.png");
        InputStream easterEggAvatarIS = resource.getInputStream();
        EASTER_EGG_IMAGE = IOUtils.toByteArray(easterEggAvatarIS);
    }

    private CloseableHttpClient getClient() {
//        return HttpClients.createDefault();
//        VS
        int timeout = 3 * 1000;
        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        return HttpClients.custom().setDefaultRequestConfig(config).build();
    }

    public String requestPage(int page) throws IOException {
        CloseableHttpClient httpClient = getClient();
        HttpGet request = new HttpGet(URL + "?page=" + page);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

    public byte[] requestImageAsBlob(String url) {
        CloseableHttpClient httpClient = getClient();
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            return IOUtils.toByteArray(content);
        } catch (IOException e) {
            //TODO: to log
            System.err.println(e);
            return EASTER_EGG_IMAGE;
        }
    }

    public String requestBase64Image(String url) {
        byte[] bytes = requestImageAsBlob(url);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
