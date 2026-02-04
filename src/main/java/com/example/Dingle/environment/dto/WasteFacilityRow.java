package com.example.Dingle.environment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WasteFacilityRow {

    @JacksonXmlProperty(localName = "fclty_se")
    private String name;

    @JacksonXmlProperty(localName = "sgg_cd")
    private String code;

    @JacksonXmlProperty(localName = "rn_adres")
    private String roadAddress;

    @JacksonXmlProperty(localName = "x")
    private Double x;

    @JacksonXmlProperty(localName = "y")
    private Double y;

    public String safeCode() { return (code == null) ? "" : code.trim(); }
}
