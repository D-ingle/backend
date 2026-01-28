package com.example.Dingle.property.dto;

import com.example.Dingle.property.type.DealType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DealRequestDTO {
    private DealType tradeType;
    private Long price;
    private Long deposit;
    private Long monthlyPrice;
}
