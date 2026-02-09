package com.example.Dingle.infra.repository;

import com.example.Dingle.infra.dto.NearbyInfraRow;
import com.example.Dingle.infra.entity.Infra;
import com.example.Dingle.infra.type.InfraType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfraRepository extends JpaRepository<Infra, Long> {
    List<Infra> findByInfraTypeIn(List<InfraType> infraTypes);

    @Query(value = """
        SELECT
            i.id AS id,
                      i.name AS name,
                      i.road_address AS roadAddress,  
                      i.latitude AS latitude,
                      i.longitude AS longitude,
                      i.infra_type AS infraType,
                      i.hospital_type AS hospitalType,
                      ST_Distance(
                        i.geom::geography,
                        ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
                      ) AS distanceMeters
                    FROM infra i
                    WHERE i.infra_type IN (:infraTypes)
                      AND ST_DWithin(
                        i.geom::geography,
                        ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
                        :radius
                      )
                    ORDER BY distanceMeters ASC
    """, nativeQuery = true)
    List<NearbyInfraRow> findNearbyInfra(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") int radius,
            @Param("infraTypes") List<String> infraTypes
    );
}
