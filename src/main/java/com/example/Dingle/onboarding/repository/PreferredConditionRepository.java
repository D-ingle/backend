package com.example.Dingle.onboarding.repository;

import com.example.Dingle.onboarding.entity.PreferredCondition;
import com.example.Dingle.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferredConditionRepository extends JpaRepository<PreferredCondition, Long> {
    @Query("SELECT p.condition.id FROM PreferredCondition p WHERE p.user.id = :userId ORDER BY p.priority ASC")
    List<Long> findConditionIdsByUserId(@Param("userId") Long userId);
    void deleteByUser(User user);
}
