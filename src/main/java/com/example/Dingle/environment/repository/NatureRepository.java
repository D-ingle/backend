package com.example.Dingle.environment.repository;

import com.example.Dingle.environment.dto.EnvironmentTotalRow;
import com.example.Dingle.environment.entity.Nature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NatureRepository extends JpaRepository<Nature, Long> {

    @Query(value = """
    SELECT
        n.id AS id,
        n.name AS name,
        n.latitude AS latitude,
        n.longitude AS longitude,
        n.nature_type AS natureType,
        ST_Distance(
            n.geom::geography,
            ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
        ) AS distanceMeters
    FROM nature n
    WHERE ST_DWithin(
        n.geom::geography,
        ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
        :radius
    )
    ORDER BY distanceMeters ASC
""", nativeQuery = true)
    List<EnvironmentTotalRow> findByNature(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") int radius
    );
}
