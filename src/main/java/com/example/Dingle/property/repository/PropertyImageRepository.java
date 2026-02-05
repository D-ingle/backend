package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.PropertyImage;
import com.example.Dingle.property.type.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    List<PropertyImage> findAllByProperty_IdInAndImageTypeOrderByProperty_IdAsc(List<Long> propertyIds, ImageType imageType);
    List<PropertyImage> findAllByProperty_IdAndImageType(Long propertyId, ImageType imageType);
    PropertyImage findByProperty_IdAndImageType(Long propertyId, ImageType imageType);
}
