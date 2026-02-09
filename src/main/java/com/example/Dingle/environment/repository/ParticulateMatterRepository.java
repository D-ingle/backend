package com.example.Dingle.environment.repository;

import com.example.Dingle.environment.entity.ParticulateMatter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticulateMatterRepository extends JpaRepository<ParticulateMatter, Long> {
    ParticulateMatter findByDistrict_Id(Long districtId);
}
