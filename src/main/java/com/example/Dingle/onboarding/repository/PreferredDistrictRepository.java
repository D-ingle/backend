package com.example.Dingle.onboarding.repository;

import com.example.Dingle.onboarding.entity.PreferredDistrict;
import com.example.Dingle.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferredDistrictRepository extends JpaRepository<PreferredDistrict, Long> {
    void deleteByUser(UserEntity user);
}
