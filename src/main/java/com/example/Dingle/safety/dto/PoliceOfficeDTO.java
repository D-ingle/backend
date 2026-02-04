package com.example.Dingle.safety.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoliceOfficeDTO {

    @JsonProperty("시도청")
    private String cityPolice;

    @JsonProperty("경찰서")
    private String policeStation;

    @JsonProperty("관서명")
    private String officeName;

    @JsonProperty("구분")
    private String type;

    @JsonProperty("전화번호")
    private String phone;

    @JsonProperty("주소")
    private String address;
}
