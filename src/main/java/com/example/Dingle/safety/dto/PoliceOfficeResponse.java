package com.example.Dingle.safety.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoliceOfficeResponse {

    private int page;
    private int perPage;
    private int totalCount;

    @JsonProperty("data")
    private List<PoliceOfficeDTO> data;
}
