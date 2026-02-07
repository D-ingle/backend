package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.dto.NearbyNoiseRow;
import com.example.Dingle.noise.entity.EmergencyCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyCenterRepository extends JpaRepository<EmergencyCenter, Long> {

    @Query(value = """
        SELECT
            e.id AS id,
            e.name AS name,
            e.latitude AS latitude,
            e.longitude AS longitude,
            ST_Distance(
                e.geom::geography,
                ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
            ) AS distanceMeters
        FROM emergency_center e
        WHERE ST_DWithin(
            e.geom::geography,
            ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
            :radius
        )
        ORDER BY distanceMeters ASC
    """, nativeQuery = true)
    List<NearbyNoiseRow> findNearbyEmergencyCenter(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") int radius
    );
}
