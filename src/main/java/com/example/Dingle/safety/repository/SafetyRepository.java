package com.example.Dingle.safety.repository;

import com.example.Dingle.infra.type.InfraType;
import com.example.Dingle.safety.entity.Safety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SafetyRepository extends JpaRepository<Safety, Long>{

    @Modifying
    @Query("delete from Safety s where s.district.id = :districtId and s.infraType = :infraType")
    void deleteByDistrictIdAndInfraType(@Param("districtId") Long districtId,
                                        @Param("infraType") InfraType infraType);
}
