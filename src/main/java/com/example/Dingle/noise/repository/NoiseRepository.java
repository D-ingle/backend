package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.entity.Noise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoiseRepository extends JpaRepository<Noise, Long> {

    @Query("""
        select coalesce(avg(n.noise), 0)
        from Noise n
        where n.district.id = :districtId
    """)
    double findDistrictAverage(@Param("districtId") Long districtId);
}
