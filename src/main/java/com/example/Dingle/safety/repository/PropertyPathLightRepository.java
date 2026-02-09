package com.example.Dingle.safety.repository;

import com.example.Dingle.safety.entity.PropertyPathLight;
import com.example.Dingle.util.dto.PointDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyPathLightRepository extends JpaRepository<PropertyPathLight, Long> {
    @Query("""
    SELECT new com.example.Dingle.util.dto.PointDTO(
        s.latitude,
        s.longitude
    )
    FROM PropertyPathLight pl
    JOIN Safety s ON pl.lightId = s.id
    WHERE pl.pathId = :pathId
      AND s.infraType = com.example.Dingle.infra.type.InfraType.SAFETY_LIGHT
    """)
    List<PointDTO> findLightsByPathId(@Param("pathId") Long pathId);
}
