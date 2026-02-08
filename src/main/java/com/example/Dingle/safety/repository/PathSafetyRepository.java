package com.example.Dingle.safety.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PathSafetyRepository {

    private final EntityManager em;

    // 경로와 교차하는 범죄주의구역 개수
    public int countCrimeIntersectPath(String pathWkt) {

        String sql = """
            SELECT COUNT(*)
            FROM crime_prone_area c
            WHERE ST_Intersects(
                c.geometry,
                ST_Transform(
                    ST_GeomFromText(?1, 4326),
                    5179
                )
            )
        """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter(1, pathWkt)
                .getSingleResult()).intValue();
    }

    // 경로 주변 CCTV 개수
    public int countCctvNearPath(String pathWkt) {

        String sql = """
            SELECT COUNT(*)
            FROM safety s
            WHERE s.infra_type = 'CCTV'
              AND ST_DWithin(
                    ST_Transform(
                        ST_SetSRID(ST_MakePoint(s.longitude, s.latitude), 4326),
                        5179
                    ),
                    ST_Transform(
                        ST_GeomFromText(?1, 4326),
                        5179
                    ),
                    10
              )
        """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter(1, pathWkt)
                .getSingleResult()).intValue();
    }

    // 경로 주변 보안등 개수
    public int countSafetyLightNearPath(String pathWkt) {

        String sql = """
            SELECT COUNT(*)
            FROM safety s
            WHERE s.infra_type = 'SAFETY_LIGHT'
              AND ST_DWithin(
                    ST_Transform(
                        ST_SetSRID(ST_MakePoint(s.longitude, s.latitude), 4326),
                        5179
                    ),
                    ST_Transform(
                        ST_GeomFromText(?1, 4326),
                        5179
                    ),
                    10
              )
        """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter(1, pathWkt)
                .getSingleResult()).intValue();
    }
}
