package com.example.Dingle.property.repository;

import com.example.Dingle.property.dto.PropertySearchFilterDTO;
import com.example.Dingle.property.entity.PropertyScore;

import java.util.List;

public interface PropertyScoreRepositoryCustom {
    List<PropertyScore> findListForMain(
            PropertySearchFilterDTO filter,
            List<Long> districtIds
    );
}
