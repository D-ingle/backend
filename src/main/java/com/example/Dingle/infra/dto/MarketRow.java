package com.example.Dingle.infra.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketRow {

    @JacksonXmlProperty(localName = "BPLCNM")
    private String name;

    @JacksonXmlProperty(localName = "RDNWHLADDR")
    private String roadAddress;

    @JacksonXmlProperty(localName = "SITEWHLADDR")
    private String siteAddress;

    @JacksonXmlProperty(localName = "X")
    private double x;

    @JacksonXmlProperty(localName = "Y")
    private double y;
}
