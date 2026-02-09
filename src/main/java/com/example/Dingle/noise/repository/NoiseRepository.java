package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.dto.SmartPoleNoiseRow;
import com.example.Dingle.noise.entity.Noise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoiseRepository extends JpaRepository<Noise, Long> {

    @Query("""
        select coalesce(avg(n.noise), 0)
        from Noise n
        where n.district.id = :districtId
    """)
    double findDistrictAverage(@Param("districtId") Long districtId);

    @Query(value = """
        SELECT
          n.id AS id,
          n.latitude AS latitude,
          n.longitude AS longitude,
          n.noise AS noise,
          n.time AS time,
          n.is_weekend AS isWeekend,
          ST_Distance(
            ST_SetSRID(ST_MakePoint(n.longitude, n.latitude), 4326)::geography,
            ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
          ) AS distanceMeter
        FROM noise n
        WHERE n.time = :time
          AND n.is_weekend = :isWeekend
          AND ST_DWithin(
            ST_SetSRID(ST_MakePoint(n.longitude, n.latitude), 4326)::geography,
            ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
            :radius
          )
        ORDER BY distanceMeter ASC
    """, nativeQuery = true)
    List<SmartPoleNoiseRow> findNearbyNoiseByTime(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") int radius,
            @Param("time") int time,
            @Param("isWeekend") boolean isWeekend
    );

    @Query(value = """
        SELECT COALESCE(AVG(noise), 0)
        FROM noise
        WHERE is_weekend = :isWeekend
          AND time = :time
          AND district_id = :districtId
    """, nativeQuery = true)
    Double findDistrictAverageNoise(
            @Param("time") int time,
            @Param("isWeekend") boolean isWeekend,
            @Param("districtId") Long districtId
    );
}
