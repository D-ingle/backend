package com.example.Dingle.safety.repository;

import com.example.Dingle.safety.entity.PropertyPathCctv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyPathCctvRepository extends JpaRepository<PropertyPathCctv, Long> {
}
