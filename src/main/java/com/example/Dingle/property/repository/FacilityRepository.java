package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.Facility;
import com.example.Dingle.property.entity.Option;
import com.example.Dingle.property.type.FacilityType;
import com.example.Dingle.property.type.OptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByFacilityTypeIn(List<FacilityType> types);
}
