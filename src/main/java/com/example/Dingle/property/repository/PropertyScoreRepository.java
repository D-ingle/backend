package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.type.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyScoreRepository extends JpaRepository<PropertyScore, Long>, PropertyScoreRepositoryCustom {
    PropertyScore findAllByPropertyId(Long propertyId);
    @Query("""
        select ps
        from PropertyScore ps
        join ps.property p
        where p.propertyType = :propertyType
            and p.district.id in :districtIds
    """)
    List<PropertyScore> findScoresByTypeAndDistricts(@Param("propertyType") PropertyType propertyType, @Param("districtIds") List<Long> districtIds);
}
