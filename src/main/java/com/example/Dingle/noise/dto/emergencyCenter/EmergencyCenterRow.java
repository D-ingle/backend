package com.example.Dingle.noise.dto.emergencyCenter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmergencyCenterRow {

    @JacksonXmlProperty(localName = "DUTYNAME")
    private String name;

    @JacksonXmlProperty(localName = "DUTYEMCLSNAME")
    private String centerCode;

    @JacksonXmlProperty(localName = "WGS84LAT")
    private Double latitude;

    @JacksonXmlProperty(localName = "WGS84LON")
    private Double longitude;
}
