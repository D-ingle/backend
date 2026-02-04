package com.example.Dingle.safety.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "safeOpenCCTV_gr")
public class SafetyOpenLightResponse {

    @JacksonXmlProperty(localName = "list_total_count")
    private int listTotalCount;

    @JacksonXmlProperty(localName = "RESULT")
    private Result result;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "row")
    private List<SafetyOpenLightRow> rows;

    @Getter
    @NoArgsConstructor
    public static class Result {

        @JacksonXmlProperty(localName = "CODE")
        private String code;

        @JacksonXmlProperty(localName = "MESSAGE")
        private String message;
    }
}
