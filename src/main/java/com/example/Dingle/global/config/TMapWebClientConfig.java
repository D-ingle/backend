package com.example.Dingle.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TMapWebClientConfig {

    @Bean(name = "tMapWebClient")
    public WebClient tMapWebClient (@Value("${tMap.travel.base-url}") String baseUrl, @Value("${tMap.travel.api-key}") String appKey) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("appKey", appKey)
                .build();
    }
}
