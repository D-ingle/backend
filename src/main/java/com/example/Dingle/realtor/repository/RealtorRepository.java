package com.example.Dingle.realtor.repository;

import com.example.Dingle.realtor.entity.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtorRepository extends JpaRepository<Realtor, Long> {
}
