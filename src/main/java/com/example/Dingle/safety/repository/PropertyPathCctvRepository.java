package com.example.Dingle.safety.repository;

import com.example.Dingle.safety.entity.PropertyPathCctv;
import com.example.Dingle.util.dto.PointDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyPathCctvRepository extends JpaRepository<PropertyPathCctv, Long> {
    @Query("""
    SELECT new com.example.Dingle.util.dto.PointDTO(
        s.latitude,
        s.longitude
    )
    FROM PropertyPathCctv pc
    JOIN Safety s ON pc.cctvId = s.id
    WHERE pc.pathId = :pathId
      AND s.infraType = com.example.Dingle.infra.type.InfraType.CCTV
    """)
    List<PointDTO> findCctvsByPathId(@Param("pathId") Long pathId);
}
