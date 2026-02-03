package com.example.Dingle.noise.dto.firestation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "TbGiWardP")
public class FireStationResponse {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "row")
    public List<FireStationRow> row;

    @JacksonXmlProperty(localName = "list_total_count")
    private int listTotalCount;
}
