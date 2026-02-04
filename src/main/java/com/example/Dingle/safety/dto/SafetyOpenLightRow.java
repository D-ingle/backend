package com.example.Dingle.safety.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SafetyOpenLightRow {

    @JacksonXmlProperty(localName = "SVCAREAID")
    private String districtName;   // 구로구

    @JacksonXmlProperty(localName = "ADDR")
    private String address;

    @JacksonXmlProperty(localName = "WGSXPT")
    private Double latitude;

    @JacksonXmlProperty(localName = "WGSYPT")
    private Double longitude;

    @JacksonXmlProperty(localName = "QTY")
    private Integer quantity;

    @JacksonXmlProperty(localName = "UPDTDATE")
    private String updatedDate;
}
