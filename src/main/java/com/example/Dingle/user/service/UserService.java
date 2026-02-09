package com.example.Dingle.user.service;

import com.example.Dingle.global.exception.AuthException;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.AuthErrorMessage;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.user.dto.DestinationDTO;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.UserRepository;
import com.example.Dingle.util.dto.GeoPointDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final KaKaoGeocodingService kaKaoGeocodingService;

    public UserService(UserRepository userRepository, KaKaoGeocodingService kaKaoGeocodingService) {
        this.userRepository = userRepository;
        this.kaKaoGeocodingService = kaKaoGeocodingService;
    }


    @Transactional
    public void saveAndUpdate(String userId, DestinationDTO destinationDTO) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorMessage.USER_NOT_EXIST));

        if(destinationDTO.getDestinationName() == null || destinationDTO.getDestinationName().isBlank() || destinationDTO.getDestinationAddress() == null ||destinationDTO.getDestinationAddress().isBlank()) {
            throw new BusinessException(BusinessErrorMessage.INVALID_DESTINATION);
        }

        GeoPointDTO geoPointDTO = kaKaoGeocodingService.getGeoPoint(destinationDTO.getDestinationAddress());

        double latitude = geoPointDTO.getLatitude();
        double longitude = geoPointDTO.getLongitude();

        user.upsertDestination(
                destinationDTO.getDestinationName(),
                destinationDTO.getDestinationAddress(),
                latitude,
                longitude
        );
    }

    @Transactional
    public DestinationDTO getDestination(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorMessage.USER_NOT_EXIST));

        return DestinationDTO.builder()
                .destinationName(user.getDestinationName())
                .destinationAddress(user.getDestinationAddress())
                .destLatitude(user.getDestLatitude())
                .destLongitude(user.getDestLongitude())
                .build();
    }
}
