package com.example.Dingle.crime.repository;

import com.example.Dingle.crime.entity.CrimeProneArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrimeProneAreaRepository extends JpaRepository<CrimeProneArea, Long> {
}
