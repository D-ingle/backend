package com.example.Dingle.district.repository;

import com.example.Dingle.district.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    boolean existsByDistrictName(String name);
    List<District> findAllByDistrictNameIn(List<String> districtNames);
    Optional<District> findByDistrictName(String districtName);
}
