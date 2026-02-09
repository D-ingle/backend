package com.example.Dingle.safety.repository;

import com.example.Dingle.safety.dto.PoliceModalResponse;
import com.example.Dingle.safety.entity.PoliceOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PoliceOfficeRepository extends JpaRepository<PoliceOffice, Long> {

    @Query(value = """
    SELECT
        p.address        AS address,
        p.latitude       AS latitude,
        p.longitude      AS longitude,
        CAST(
          ST_Distance(
            ST_Transform(
              ST_SetSRID(ST_Point(p.longitude, p.latitude), 4326),
              5179
            ),
            ST_Transform(
              ST_SetSRID(ST_Point(:lon, :lat), 4326),
              5179
            )
          ) * 1.5 / 1.3 AS INTEGER
        ) AS durationTime
    FROM police_office p
    WHERE ST_DWithin(
        ST_Transform(
          ST_SetSRID(ST_Point(p.longitude, p.latitude), 4326),
          5179
        ),
        ST_Transform(
          ST_SetSRID(ST_Point(:lon, :lat), 4326),
          5179
        ),
        500
    )
    ORDER BY durationTime
    LIMIT 3
    """, nativeQuery = true)
    List<PoliceModalResponse> findNearbyPolices(
            @Param("lat") double lat,
            @Param("lon") double lon
    );
}
