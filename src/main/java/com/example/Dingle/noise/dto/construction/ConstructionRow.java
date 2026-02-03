package com.example.Dingle.noise.dto.construction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConstructionRow {

    @JacksonXmlProperty(localName = "cntwrk_nm")
    private String name;

    @JacksonXmlProperty(localName = "wrk_adres")
    private String roadAddress;

    @JacksonXmlProperty(localName = "strwrk_de")
    private String startDate;

    @JacksonXmlProperty(localName = "compet_de")
    private String endDate;

    @JacksonXmlProperty(localName = "x")
    private Double x;

    @JacksonXmlProperty(localName = "y")
    private Double y;
}
