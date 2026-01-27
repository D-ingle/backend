package com.example.Dingle.district.repository;

import com.example.Dingle.district.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    boolean existsByDistrictName(String name);
}
