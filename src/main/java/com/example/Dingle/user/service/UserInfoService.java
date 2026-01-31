package com.example.Dingle.user.service;

import com.example.Dingle.global.exception.AuthException;
import com.example.Dingle.global.message.AuthErrorMessage;
import com.example.Dingle.onboarding.repository.PreferredConditionRepository;
import com.example.Dingle.user.dto.UserInfoDTO;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.UserRepository;
import com.example.Dingle.user.type.PreferredType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    private static final PreferredType DEFAULT_PREFERRED_TYPE = PreferredType.APT;
    private static final List<Long> DEFAULT_CONDITION_IDS = List.of(1L, 2L, 3L);


    private final UserRepository userRepository;
    private final PreferredConditionRepository preferredConditionRepository;

    public UserInfoService(UserRepository userRepository, PreferredConditionRepository preferredConditionRepository) {
        this.userRepository = userRepository;
        this.preferredConditionRepository = preferredConditionRepository;
    }

    public UserInfoDTO getUserInfo(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorMessage.USER_NOT_EXIST));

        boolean isOnboard = (user.getOnboardedAt() == null);

        PreferredType preferredType;
        List<Long> conditionIds;

        if (isOnboard) {
            preferredType = DEFAULT_PREFERRED_TYPE;
            conditionIds = DEFAULT_CONDITION_IDS;
        } else {
            preferredType = user.getPreferredType();
            conditionIds = preferredConditionRepository.findConditionIdsByUserId(user.getId());
        }

        return UserInfoDTO.builder()
                .userName(user.getUsername())
                .preferredType(preferredType)
                .preferredConditions(conditionIds)
                .build();

    }
}
