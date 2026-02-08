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
            SELECT COALESCE(MAX(c.risk_level), 0)
            FROM crime_prone_area c
            WHERE ST_Contains(
                c.geometry,
                ST_Transform(
                    ST_SetSRID(ST_MakePoint(?1, ?2), 4326),
                    5179
                )
            )
        """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter(1, lon)
                .setParameter(2, lat)
                .getSingleResult()).intValue();
    }


    // 범죄주의구역 300m 내 존재 여부
    public boolean existsCrimeWithin300m(double lat, double lon) {

        String sql = """
            SELECT EXISTS (
                SELECT 1
                FROM crime_prone_area c
                WHERE ST_DWithin(
                    c.geometry,
                    ST_Transform(
                        ST_SetSRID(ST_MakePoint(?1, ?2), 4326),
                        5179
                    ),
                    300
                )
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
            SELECT COALESCE(
                MIN(
                    ST_Distance(
                        ST_Transform(
                            ST_SetSRID(ST_MakePoint(p.longitude, p.latitude), 4326),
                            5179
                        ),
                        ST_Transform(
                            ST_SetSRID(ST_MakePoint(?1, ?2), 4326),
                            5179
                        )
                    )
                ),
                999999
            )
            FROM police_office p
        """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter(1, lon)
                .setParameter(2, lat)
                .getSingleResult()).intValue();
    }

    // CCTV 50m 내 개수
    public int countCctvWithin50m(double lat, double lon) {

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
                        ST_SetSRID(ST_MakePoint(?1, ?2), 4326),
                        5179
                    ),
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
              AND ST_DWithin(
                    ST_Transform(
                        ST_SetSRID(ST_MakePoint(s.longitude, s.latitude), 4326),
                        5179
                    ),
                    ST_Transform(
                        ST_SetSRID(ST_MakePoint(?1, ?2), 4326),
                        5179
                    ),
                    50
              )
        """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter(1, lon)
                .setParameter(2, lat)
                .getSingleResult()).intValue();
    }
}
