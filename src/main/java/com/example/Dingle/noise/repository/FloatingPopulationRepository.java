package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.entity.FloatingPopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FloatingPopulationRepository extends JpaRepository<FloatingPopulation, Long> {
    @Query("""
        select coalesce(avg(f.population), 0)
        from FloatingPopulation f
        where f.district.id = :districtId
    """)
    double findDistrictAverage(@Param("districtId") Long districtId);
}
