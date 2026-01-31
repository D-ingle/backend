package com.example.Dingle.preference.repository;

import com.example.Dingle.preference.entity.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long> {
    boolean existsByConditionName(String name);
    Long countByIdIn(List<Long> ids);
}
