package com.example.Dingle.safety.repository;

import com.example.Dingle.safety.entity.CrimeProneArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CrimeProneAreaRepository extends JpaRepository<CrimeProneArea, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO crime_prone_area (
            district_id,
            crime_type,
            risk_level,
            geometry
        ) VALUES (
            :districtId,
            :crimeType,
            :riskLevel,
            ST_SetSRID(
                 ST_GeomFromText(:wkt),
                 5179
             )
        )
        """, nativeQuery = true)
    void insertNative(
            @Param("districtId") Long districtId,
            @Param("crimeType") String crimeType,
            @Param("riskLevel") int riskLevel,
            @Param("wkt") String wkt
    );
}
