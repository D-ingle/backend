package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.PropertyDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyDescriptionRepository extends JpaRepository<PropertyDescription, Long> {
}
