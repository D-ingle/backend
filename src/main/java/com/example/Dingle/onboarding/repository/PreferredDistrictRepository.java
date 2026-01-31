package com.example.Dingle.onboarding.repository;

import com.example.Dingle.onboarding.entity.PreferredDistrict;
import com.example.Dingle.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferredDistrictRepository extends JpaRepository<PreferredDistrict, Long> {
    @Query("select pd.district.id from PreferredDistrict pd where pd.user.id = :userPk")
    List<Long> findPreferredDistrictByUserPk(long userPk);
    void deleteByUser(User user);
}
