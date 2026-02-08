package com.example.Dingle.safety.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoPlaceResponse {

    private List<Document> documents;

    @Getter
    @NoArgsConstructor
    public static class Document {
        private String id;
        private String place_name;
        private String category_group_code;
        private String category_group_name;

        // 경도(x), 위도(y)
        private String x;
        private String y;

        private String distance;
    }
}
