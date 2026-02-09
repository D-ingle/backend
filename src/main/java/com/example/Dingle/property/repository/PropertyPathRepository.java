package com.example.Dingle.property.repository;

import com.example.Dingle.safety.entity.PropertyPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyPathRepository extends JpaRepository<PropertyPath, Long> {
    Optional<PropertyPath> findByProperty_Id(Long propertyId);
    @Query(value = """
        SELECT ST_AsGeoJSON(path_wkt)
        FROM property_path
        WHERE id = :pathId
        """, nativeQuery = true)
    String findPathGeoJsonByPathId(@Param("pathId") Long pathId);
}
