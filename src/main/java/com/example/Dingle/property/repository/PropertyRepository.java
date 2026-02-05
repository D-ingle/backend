package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("""
        SELECT DISTINCT p FROM Property p
        JOIN FETCH p.deal
        WHERE p.id IN :ids
    """)
    List<Property> findAllByIdInWithDetails(@Param("ids") List<Long> ids);

    @Query("""
        SELECT DISTINCT p FROM Property p
        LEFT JOIN FETCH p.score
        where p.id IN :ids
    """)
    List<Property> findAllByIdInWithScore(@Param("ids") List<Long> ids);

}
