package ru.olegivanov;

import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        final String url = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
        ObjectMapper mapper = new ObjectMapper();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet request = new HttpGet(url);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        request.setHeader(HttpHeaders.CONTENT_ENCODING, "UTF-8");
        CloseableHttpResponse response = httpClient.execute(request);
        //Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
        List<CatsPost> catsPost = mapper.readValue(
                response.getEntity().getContent(), new
                        TypeReference<>() {
                        });
        //catsPost.forEach(System.out::println);
        catsPost.stream()
                .filter(value -> value.getUpvotes() > 0)
                .forEach(System.out::println);
        System.out.println("Все готово!!");
    }
}