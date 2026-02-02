package com.example.Dingle.user.dto;

import com.example.Dingle.property.type.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private String userName;
    private PropertyType propertyType;
    private List<Long> preferredConditions;

}
