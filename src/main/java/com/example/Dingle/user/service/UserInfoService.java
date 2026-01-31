package com.example.Dingle.user.service;

import com.example.Dingle.global.exception.AuthException;
import com.example.Dingle.global.message.AuthErrorMessage;
import com.example.Dingle.onboarding.repository.PreferredConditionRepository;
import com.example.Dingle.user.dto.UserInfoDTO;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final PreferredConditionRepository preferredConditionRepository;

    public UserInfoService(UserRepository userRepository, PreferredConditionRepository preferredConditionRepository) {
        this.userRepository = userRepository;
        this.preferredConditionRepository = preferredConditionRepository;
    }

    public UserInfoDTO getUserInfo(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorMessage.USER_NOT_EXIST));

        List<Long> conditionIds = preferredConditionRepository.findConditionIdsByUserId(user.getId());

        return UserInfoDTO.builder()
                .userName(user.getUsername())
                .preferredType(user.getPreferredType())
                .preferredConditions(conditionIds)
                .build();

    }
}
