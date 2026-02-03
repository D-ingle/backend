package com.example.Dingle.util.map;

import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class OpenApiXmlFetcher {

    private static final Charset EUC_KR = Charset.forName("EUC-KR");

    private OpenApiXmlFetcher() {}

    public static String fetch(RestTemplate restTemplate, String url) {
        byte[] bytes = restTemplate.getForObject(url, byte[].class);

        if (bytes == null || bytes.length == 0) {
            return "";
        }

        String utf8 = new String(bytes, StandardCharsets.UTF_8);

        if (utf8.indexOf('\uFFFD') >= 0) {
            return new String(bytes, EUC_KR);
        }
        return utf8;
    }
}
