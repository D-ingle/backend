package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.Option;
import com.example.Dingle.property.type.OptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByOptionTypeIn(List<OptionType> types);
}
