package com.example.Dingle.safety.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HomeSafetyRepository {

    private final EntityManager em;

    // 범죄주의구역 내부 최대 리스크 레벨
    public int findInsideMaxRiskLevel(double lat, double lon) {
        String sql = """
        WITH target AS (
          SELECT ST_Transform(ST_SetSRID(ST_MakePoint(?1, ?2), 4326), 5179) AS g
        )
        SELECT COALESCE(MAX(c.risk_level), 0)
        FROM crime_prone_area c, target
        WHERE c.geometry && target.g
          AND ST_Contains(c.geometry, target.g)
    """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter(1, lon)
                .setParameter(2, lat)
                .getSingleResult()).intValue();
    }


    // 범죄주의구역 300m 내 존재 여부
    public boolean existsCrimeWithin300m(double lat, double lon) {
        String sql = """
        WITH target AS (
          SELECT ST_Transform(ST_SetSRID(ST_MakePoint(?1, ?2), 4326), 5179) AS g
        )
        SELECT EXISTS (
          SELECT 1
          FROM crime_prone_area c, target
          WHERE c.geometry && ST_Expand(target.g, 300)
            AND ST_DWithin(c.geometry, target.g, 300)
        )
    """;

        return (Boolean) em.createNativeQuery(sql)
                .setParameter(1, lon)
                .setParameter(2, lat)
                .getSingleResult();
    }


    // 경찰서 / 파출소 최소 거리
    public int findNearestPoliceDistanceMeter(double lat, double lon) {
        String sql = """
        WITH target AS (
          SELECT ST_Transform(ST_SetSRID(ST_MakePoint(?1, ?2), 4326), 5179) AS g
        )
        SELECT COALESCE(
          ST_Distance(p.geom_5179, target.g),
          999999
        )
        FROM police_office p, target
        WHERE p.geom_5179 IS NOT NULL
        ORDER BY p.geom_5179 <-> target.g
        LIMIT 1
    """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter(1, lon)
                .setParameter(2, lat)
                .getSingleResult()).intValue();
    }

    public int countCctvWithin50m(double lat, double lon) {
        String sql = """
        SELECT COUNT(*)
        FROM safety s
        WHERE s.infra_type = 'CCTV'
          AND s.geom_5179 IS NOT NULL
          AND ST_DWithin(
                s.geom_5179,
                ST_Transform(ST_SetSRID(ST_MakePoint(?1, ?2), 4326), 5179),
                50
          )
    """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter(1, lon)
                .setParameter(2, lat)
                .getSingleResult()).intValue();
    }

    // 보안등 50m 내 개수
    public int countSafetyLightWithin50m(double lat, double lon) {
        String sql = """
        SELECT COUNT(*)
        FROM safety s
        WHERE s.infra_type = 'SAFETY_LIGHT'
          AND s.geom_5179 IS NOT NULL
          AND ST_DWithin(
                s.geom_5179,
                ST_Transform(ST_SetSRID(ST_MakePoint(?1, ?2), 4326), 5179),
                50
          )
    """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter(1, lon)
                .setParameter(2, lat)
                .getSingleResult()).intValue();
    }
}
