package com.example.Dingle.onboarding.repository;

import com.example.Dingle.onboarding.entity.PreferredCondition;
import com.example.Dingle.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferredConditionRepository extends JpaRepository<PreferredCondition, Long> {
    void deleteByUser(UserEntity user);
}
