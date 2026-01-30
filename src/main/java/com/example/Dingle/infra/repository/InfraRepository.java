package com.example.Dingle.infra.repository;

import com.example.Dingle.infra.entity.Infra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfraRepository extends JpaRepository<Infra, Long> {
}
