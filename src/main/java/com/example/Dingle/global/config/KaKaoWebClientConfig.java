package com.example.Dingle.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class KaKaoWebClientConfig {

    @Bean
    public WebClient kaKaoWebClient(@Value("${kakao.base-url}") String baseUrl, @Value("${kakao.api-key}") String apiKey) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + apiKey)
                .build();
    }
}
