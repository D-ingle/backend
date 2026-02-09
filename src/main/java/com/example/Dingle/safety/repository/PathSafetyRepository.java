package com.example.Dingle.safety.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PathSafetyRepository {

    private final EntityManager em;

    // 경로와 교차하는 범죄주의구역 ID
    public List<Long> findCrimeIdsIntersectPath(String pathWkt) {

        String sql = """
            SELECT c.id
            FROM crime_prone_area c
            WHERE ST_Intersects(
                c.geometry,
                ST_Transform(
                    ST_GeomFromText(?1, 4326),
                    5179
                )
            )
        """;

        List<?> result = em.createNativeQuery(sql)
                .setParameter(1, pathWkt)
                .getResultList();

        return result.stream()
                .map(r -> ((Number) r).longValue())
                .toList();
    }

    // 경로 주변 CCTV ID
    public List<Long> findCctvIdsNearPath(String pathWkt) {

        String sql = """
            SELECT s.id
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

        List<?> result = em.createNativeQuery(sql)
                .setParameter(1, pathWkt)
                .getResultList();

        return result.stream()
                .map(r -> ((Number) r).longValue())
                .toList();
    }

    // 경로 주변 보안등 ID
    public List<Long> findSafetyLightIdsNearPath(String pathWkt) {

        String sql = """
            SELECT s.id
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

        List<?> result = em.createNativeQuery(sql)
                .setParameter(1, pathWkt)
                .getResultList();

        return result.stream()
                .map(r -> ((Number) r).longValue())
                .toList();
    }

    // 경로 ⬌ CCTV 매핑 저장
    @Transactional
    public void insertCctvMapping(Long pathId, List<Long> cctvIds) {

        if (cctvIds.isEmpty()) return;

        String sql = """
            INSERT INTO property_path_cctv (path_id, cctv_id)
            SELECT :pathId, UNNEST(CAST(:cctvIds AS BIGINT[]))
            ON CONFLICT DO NOTHING
        """;

        em.createNativeQuery(sql)
                .setParameter("pathId", pathId)
                .setParameter("cctvIds", cctvIds.toArray(new Long[0]))
                .executeUpdate();
    }

    // 경로 ⬌ 범죄주의구역 매핑 저장
    @Transactional
    public void insertCrimeMapping(Long pathId, List<Long> crimeIds) {

        if (crimeIds.isEmpty()) return;

        String sql = """
            INSERT INTO property_path_crime_area (path_id, crime_area_id)
            SELECT :pathId, UNNEST(CAST(:crimeIds AS BIGINT[]))
            ON CONFLICT DO NOTHING
        """;

        em.createNativeQuery(sql)
                .setParameter("pathId", pathId)
                .setParameter("crimeIds", crimeIds.toArray(new Long[0]))
                .executeUpdate();
    }

    // 경로 ⬌ 보안등 매핑 저장
    @Transactional
    public void insertSafetyLightMapping(Long pathId, List<Long> lightIds) {

        if (lightIds.isEmpty()) return;

        String sql = """
            INSERT INTO property_path_light (path_id, light_id)
            SELECT :pathId, UNNEST(CAST(:lightIds AS BIGINT[]))
            ON CONFLICT DO NOTHING
        """;

        em.createNativeQuery(sql)
                .setParameter("pathId", pathId)
                .setParameter("lightIds", lightIds.toArray(new Long[0]))
                .executeUpdate();
    }


    // 경로 저장 (geometry)
    @Transactional
    public Long insertPropertyPath(Long propertyId, String pathWkt) {

        String sql = """
            INSERT INTO property_path (property_id, path_wkt)
            VALUES (
                :propertyId,
                ST_SetSRID(ST_GeomFromText(:pathWkt), 4326)
            )
            RETURNING id
        """;

        return ((Number) em.createNativeQuery(sql)
                .setParameter("propertyId", propertyId)
                .setParameter("pathWkt", pathWkt)
                .getSingleResult()).longValue();
    }
}
