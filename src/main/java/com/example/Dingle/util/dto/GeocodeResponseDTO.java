package com.example.Dingle.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResponseDTO {

    private List<Document> documents;

    @Getter
    @NoArgsConstructor
    public static class Document {
        private String x;
        private String y;
    }
}
