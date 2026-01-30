package com.example.Dingle.infra.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SafeOpenCctvRow {

    @JacksonXmlProperty(localName = "SVCAREAID")
    private String svcAreaId;

    @JacksonXmlProperty(localName = "WGSXPT")
    private double latitude;

    @JacksonXmlProperty(localName = "WGSYPT")
    private double longitude;
}
