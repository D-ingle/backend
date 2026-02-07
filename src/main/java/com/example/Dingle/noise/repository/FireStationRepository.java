package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.dto.NearbyNoiseRow;
import com.example.Dingle.noise.entity.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {

    @Query(value = """
        SELECT
            f.id AS id,
            f.name AS name,
            f.latitude AS latitude,
            f.longitude AS longitude,
            ST_Distance(
                f.geom::geography,
                ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
            ) AS distanceMeters
        FROM firestation f
        WHERE ST_DWithin(
            f.geom::geography,
            ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
            :radius
        )
        ORDER BY distanceMeters ASC
    """, nativeQuery = true)
    List<NearbyNoiseRow> findNearbyFireStation(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") int radius
    );
}
