package com.example.Dingle.infra.repository;

import com.example.Dingle.infra.entity.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficRepository extends JpaRepository<Traffic, Long> {
    List<Traffic> findByPropertyId(Long propertyId);
}
