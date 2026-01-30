package com.example.Dingle.user.service;

import com.example.Dingle.global.exception.AuthException;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.AuthErrorMessage;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.user.entity.SavedProperty;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.SavedPropertyRepository;
import com.example.Dingle.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SavedPropertyService {

    private final SavedPropertyRepository savedPropertyRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(String userId, Long propertyId) {

        // 유저 없음
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorMessage.USER_NOT_EXIST));

        // 매물 없음
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        // 이미 찜함
        if (savedPropertyRepository.existsByUserIdAndPropertyId(userId, propertyId)) {
            throw new BusinessException(BusinessErrorMessage.ALREADY_ZZIMED);
        }

        SavedProperty savedProperty = SavedProperty.create(userId, propertyId);
        savedPropertyRepository.save(savedProperty);
    }

    @Transactional
    public void unsave(String userId, Long propertyId) {
        // 찜한 매물이 아님
        SavedProperty savedProperty = savedPropertyRepository.findByUserIdAndPropertyId(userId, propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.NOT_ZZIMED));

        savedPropertyRepository.deleteByUserIdAndPropertyId(userId, propertyId);
    }
}
