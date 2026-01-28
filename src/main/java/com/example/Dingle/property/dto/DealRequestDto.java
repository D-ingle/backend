package com.example.Dingle.property.dto;

import com.example.Dingle.property.type.DealType;
import lombok.Getter;

@Getter
public class DealRequestDto {
    private DealType tradeType;
    private Long price;
    private Long deposit;
    private Long monthlyPrice;
}
