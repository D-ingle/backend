package com.example.Dingle.user.service;

import com.example.Dingle.global.exception.AuthException;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.AuthErrorMessage;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.user.dto.DestinationDTO;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public void saveAndUpdate(String userId, DestinationDTO destinationDTO) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorMessage.USER_NOT_EXIST));

        if(destinationDTO.getDestinationName() == null || destinationDTO.getDestinationAddress() == null) {
            throw new BusinessException(BusinessErrorMessage.INVALID_DESTINATION);
        }

        user.upsertDestination(
                destinationDTO.getDestinationName(),
                destinationDTO.getDestinationAddress()
        );
    }

    @Transactional
    public DestinationDTO getDestination(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorMessage.USER_NOT_EXIST));

        return DestinationDTO.builder()
                .destinationName(user.getDestinationName())
                .destinationAddress(user.getDestinationAddress())
                .build();
    }
}
