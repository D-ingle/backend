package com.example.Dingle.onboarding.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.AuthException;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.AuthErrorMessage;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.onboarding.dto.OnboardRequestDTO;
import com.example.Dingle.onboarding.entity.PreferredCondition;
import com.example.Dingle.onboarding.entity.PreferredDistrict;
import com.example.Dingle.onboarding.repository.PreferredConditionRepository;
import com.example.Dingle.onboarding.repository.PreferredDistrictRepository;
import com.example.Dingle.preference.repository.ConditionRepository;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.Dingle.global.message.BusinessErrorMessage.CONDITION_NOT_EXISTS;

@Service
public class OnboardingService {

    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;
    private final PreferredDistrictRepository preferredDistrictRepository;
    private final ConditionRepository conditionRepository;
    private final PreferredConditionRepository preferredConditionRepository;

    public OnboardingService(UserRepository userRepository, DistrictRepository districtRepository, PreferredDistrictRepository preferredDistrictRepository, ConditionRepository conditionRepository, PreferredConditionRepository preferredConditionRepository) {
        this.userRepository = userRepository;
        this.districtRepository = districtRepository;
        this.preferredDistrictRepository = preferredDistrictRepository;
        this.conditionRepository = conditionRepository;
        this.preferredConditionRepository = preferredConditionRepository;
    }

    @Transactional
    public void saveOnboardInfo(String userId, OnboardRequestDTO dto) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorMessage.USER_NOT_EXIST));
        user.setPropertyType(dto.getPropertyType());

        if (user.getOnboardedAt() != null) {
            throw new BusinessException(BusinessErrorMessage.ALREADY_ONBOARDED);
        }

        List<District> districts = districtRepository.findAllByDistrictNameIn(dto.getPreferredDistricts());
        List<PreferredDistrict> preferredDistricts = districts.stream()
                .map(district -> new PreferredDistrict(user, district))
                .collect(Collectors.toList());

        preferredDistrictRepository.saveAll(preferredDistricts);

        List<Long> ids = dto.getPreferredConditions();
        long count = conditionRepository.countByIdIn(ids);
        if(count != ids.size()) {
            throw new BusinessException(CONDITION_NOT_EXISTS);
        }

        List<PreferredCondition> preferredConditions = IntStream.range(0, ids.size())
                        .mapToObj(i -> {
                            Long conditionId = ids.get(i);
                            int priority = i + 1;

                            return new PreferredCondition(
                                    user,
                                    conditionRepository.getReferenceById(conditionId),
                                    priority
                            );
                        }).toList();

        preferredConditionRepository.saveAll(preferredConditions);
        user.setOnboardedAt(LocalDateTime.now());
    }
}
