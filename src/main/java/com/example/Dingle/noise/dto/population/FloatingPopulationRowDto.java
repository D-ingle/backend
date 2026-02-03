package com.example.Dingle.noise.dto.population;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FloatingPopulationRowDto {

    @JacksonXmlProperty(localName = "MODEL_NM")
    private String modelNm;

    @JacksonXmlProperty(localName = "SERIAL_NO")
    private String serialNo;

    @JacksonXmlProperty(localName = "SENSING_TIME")
    private String sensingTime;

    @JacksonXmlProperty(localName = "REGION")
    private String region;

    @JacksonXmlProperty(localName = "AUTONOMOUS_DISTRICT")
    private String autonomousDistrict;

    @JacksonXmlProperty(localName = "ADMINISTRATIVE_DISTRICT")
    private String administrativeDistrict;

    @JacksonXmlProperty(localName = "VISITOR_COUNT")
    private Integer visitorCount;

    @JacksonXmlProperty(localName = "REG_DTTM")
    private String regDttm;
}
