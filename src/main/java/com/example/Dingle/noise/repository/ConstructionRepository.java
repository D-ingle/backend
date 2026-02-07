package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.dto.NearbyConstructionRow;
import com.example.Dingle.noise.entity.Construction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstructionRepository extends JpaRepository<Construction, Long> {

    @Query(value = """
        SELECT
            c.id AS id,
            c.name AS name,
            c.latitude AS latitude,
            c.longitude AS longitude,
            c.end_date AS endDate,
            ST_Distance(
                c.geom::geography,
                ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
            ) AS distanceMeters
        FROM construction c
        WHERE ST_DWithin(
            c.geom::geography,
            ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
            :radius
        )
        ORDER BY distanceMeters ASC
    """, nativeQuery = true)
    List<NearbyConstructionRow> findNearbyConstruction(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") int radius
    );
}
