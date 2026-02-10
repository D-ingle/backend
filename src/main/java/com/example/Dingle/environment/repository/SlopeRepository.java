package com.example.Dingle.environment.repository;

import com.example.Dingle.environment.entity.Slope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SlopeRepository extends JpaRepository<Slope, Long> {
    Optional<Slope> findByPropertyId(Long propertyId);
    Slope findByProperty_Id(Long propertyId);
}
