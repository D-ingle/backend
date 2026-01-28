package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.PropertyOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyOptionRepository extends JpaRepository<PropertyOption, Long> {
}
