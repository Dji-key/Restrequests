package ru.voskhod.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.voskhod.controller.MainController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Component
public class NetworkRequester {

    private static final Logger logger = LogManager.getLogger(MainController.class);

    private final String URL = "https://reqres.in/api/users";
    private final byte[] EASTER_EGG_IMAGE;
    private int TIMEOUT = 3 * 1000;
    private final CloseableHttpClient HTTP_CLIENT = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setConnectTimeout(TIMEOUT)
                    .setSocketTimeout(TIMEOUT).build())
            .build();

    public NetworkRequester() throws IOException {
        Resource resource = new ClassPathResource("images/kinda-easter-egg.png");
        InputStream easterEggAvatarIS = resource.getInputStream();
        EASTER_EGG_IMAGE = IOUtils.toByteArray(easterEggAvatarIS);
    }

    public String requestPage(int page) throws IOException {
        HttpGet request = new HttpGet(URL + "?page=" + page);
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

    public byte[] requestImageAsBlob(String url) {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            return IOUtils.toByteArray(content);
        } catch (IOException e) {
            logger.error("Download image failed, returned default image");
            return EASTER_EGG_IMAGE;
        }
    }

    public String requestBase64Image(String url) {
        byte[] bytes = requestImageAsBlob(url);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String requestUser(Long id) throws IOException {
        HttpGet request = new HttpGet(URL + "/" + id);
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }
}
