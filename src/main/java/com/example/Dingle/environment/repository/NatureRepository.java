package com.example.Dingle.environment.repository;

import com.example.Dingle.environment.entity.Nature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NatureRepository extends JpaRepository<Nature, Long> {
}
