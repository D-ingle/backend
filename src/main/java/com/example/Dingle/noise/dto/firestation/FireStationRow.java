package com.example.Dingle.noise.dto.firestation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FireStationRow {

    @JacksonXmlProperty(localName = "DEPT_NM")
    private String name;

    @JacksonXmlProperty(localName = "XCRD")
    private double x;

    @JacksonXmlProperty(localName = "YCRD")
    private double y;
}
