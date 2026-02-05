package com.example.Dingle.infra.repository;

import com.example.Dingle.infra.entity.Infra;
import com.example.Dingle.infra.type.InfraType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfraRepository extends JpaRepository<Infra, Long> {
    List<Infra> findByInfraTypeIn(List<InfraType> infraTypes);
}
