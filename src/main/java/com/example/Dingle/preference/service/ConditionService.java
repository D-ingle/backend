package com.example.Dingle.preference.service;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.preference.dto.request.ConditionRegisterDto;
import com.example.Dingle.preference.entity.Condition;
import com.example.Dingle.preference.repository.ConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConditionService {

    private final ConditionRepository conditionRepository;

    public void register(ConditionRegisterDto request) {
        if (conditionRepository.existsByConditionName(request.getConditionName())) {
            throw new BusinessException(BusinessErrorMessage.DUPLICATE_DISTRICT);
        }

        conditionRepository.save(
                new Condition(request.getConditionName())
        );
    }
}
