package com.example.Dingle.environment.repository;

import com.example.Dingle.environment.entity.CrimeProneArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrimeProneAreaRepository extends JpaRepository<CrimeProneArea, Long> {
}
