package com.example.Dingle.environment.util;

import com.example.Dingle.environment.type.CrimeType;

public class CrimeTypeMapper {

    public static CrimeType from(String typeNm) {

        if (typeNm == null || typeNm.isBlank()) {
            return CrimeType.TOTAL;
        }

        if (typeNm.contains("절도")) {
            return CrimeType.THEFT;
        }

        if (typeNm.contains("성폭력") || typeNm.contains("성")) {
            return CrimeType.SEXUAL;
        }

        if (typeNm.contains("폭력")) {
            return CrimeType.VIOLENT;
        }

        return CrimeType.TOTAL;
    }
}
