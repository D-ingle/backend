package com.example.Dingle.infra.dto.hospital;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalRow {

    @JacksonXmlProperty(localName = "BPLCNM")
    private String name;

    @JacksonXmlProperty(localName = "UPTAENM")
    private String hospitalType;

    @JacksonXmlProperty(localName = "RDNWHLADDR")
    private String roadAddress;

    @JacksonXmlProperty(localName = "SITEWHLADDR")
    private String siteAddress;

    @JacksonXmlProperty(localName = "X")
    private Double x;

    @JacksonXmlProperty(localName = "Y")
    private Double y;
}
