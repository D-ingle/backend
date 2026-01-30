package com.example.Dingle.environment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrimeApiItemDto {
    private String SN;
    private String TYPE_NM;
    private String TYPE_CD;
    private String GRD;
    private String STDG_CTPV_CD;
    private String STDG_SGG_CD;
    private String GEOM;
}
