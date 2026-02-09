package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.dto.SmartPolePopulationRow;
import com.example.Dingle.noise.entity.FloatingPopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloatingPopulationRepository extends JpaRepository<FloatingPopulation, Long> {
    @Query("""
        select coalesce(avg(f.population), 0)
        from FloatingPopulation f
        where f.district.id = :districtId
    """)
    double findDistrictAverage(@Param("districtId") Long districtId);


    @Query(value = """
    SELECT
      fp.id AS id,
      fp.latitude AS latitude,
      fp.longitude AS longitude,
      fp.population AS population,
      fp.time AS time,
      fp.is_weekend AS isWeekend,
      ST_Distance(
        ST_SetSRID(ST_MakePoint(fp.longitude, fp.latitude), 4326)::geography,
        ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
      ) AS distanceMeter
    FROM floating_population fp
    WHERE fp.time = :time
      AND fp.is_weekend = :isWeekend
      AND ST_DWithin(
        ST_SetSRID(ST_MakePoint(fp.longitude, fp.latitude), 4326)::geography,
        ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
        :radius
      )
    ORDER BY distanceMeter ASC
""", nativeQuery = true)
    List<SmartPolePopulationRow> findNearbyPopulationByTime(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") int radius,
            @Param("time") int time,
            @Param("isWeekend") boolean isWeekend
    );

    @Query(value = """
        SELECT COALESCE(AVG(population), 0)
        FROM floating_population
        WHERE is_weekend = :isWeekend
          AND time = :time
          AND district_id = :districtId
    """, nativeQuery = true)
    Double findDistrictAveragePopulation(
            @Param("time") int time,
            @Param("isWeekend") boolean isWeekend,
            @Param("districtId") Long districtId
    );
}
