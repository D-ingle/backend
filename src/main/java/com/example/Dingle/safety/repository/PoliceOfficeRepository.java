package com.example.Dingle.safety.repository;

import com.example.Dingle.safety.entity.PoliceOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliceOfficeRepository extends JpaRepository<PoliceOffice, Long> {
}
