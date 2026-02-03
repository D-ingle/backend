package com.example.Dingle.noise.dto.noise;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "row")
public class NoiseRowDto {

    @JacksonXmlProperty(localName = "SN")
    private String sn;

    @JacksonXmlProperty(localName = "CGG")
    private String cgg;

    @JacksonXmlProperty(localName = "MSRMT_HR")
    private String measuredAt;

    @JacksonXmlProperty(localName = "AVG_NIS")
    private Double avgNoise;
}
