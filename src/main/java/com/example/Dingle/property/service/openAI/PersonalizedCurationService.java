package com.example.Dingle.property.service.openAI;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.onboarding.entity.PreferredCondition;
import com.example.Dingle.onboarding.repository.PreferredConditionRepository;
import com.example.Dingle.property.dto.openAI.ConditionEvaluation;
import com.example.Dingle.property.dto.openAI.CurationEvaluationResult;
import com.example.Dingle.property.dto.openAI.CurationResponse;
import com.example.Dingle.property.entity.PropertyDescription;
import com.example.Dingle.property.repository.PropertyDescriptionRepository;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalizedCurationService {

    private final PreferredConditionRepository preferredConditionRepository;
    private final PropertyDescriptionRepository propertyDescriptionRepository;
    private final UserRepository userRepository;
    private final OpenAiCurationService openAiCurationService;

    public CurationResponse curate(Long propertyId, String userId) {

        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                        new BusinessException(BusinessErrorMessage.USER_NOT_EXISTS));
        log.info("[Curation] userId={}, propertyId={}", userId, propertyId);

        List<PreferredCondition> preferences = preferredConditionRepository.findByUserOrderByPriorityAsc(user);
        log.info("[Curation] preference size={}", preferences.size());


        PropertyDescription description = propertyDescriptionRepository.findByPropertyId(propertyId)
                        .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_EXPLANATION_NOT_EXISTS));
        log.info("[Curation-Desc] ENV={}", description.getEnvironmentDescription());
        log.info("[Curation-Desc] SAFETY={}", description.getSafetyDescription());
        log.info("[Curation-Desc] CONVENIENCE={}", description.getConvenienceDescription());
        log.info("[Curation-Desc] NOISE={}", description.getNoiseDescription());
        log.info("[Curation-Desc] ACCESSIBILITY={}", description.getAccessibilityDescription());

        AtomicInteger rankCounter = new AtomicInteger(1);

        List<ConditionEvaluation> evaluations = preferences.stream()
                .sorted(Comparator.comparingInt(PreferredCondition::getPriority))
                .limit(3)
                .map(pc -> {

                    int rank = rankCounter.getAndIncrement();

                    int weight = switch (rank) {
                        case 1 -> 50;
                        case 2 -> 30;
                        case 3 -> 20;
                        default -> 0;
                    };

                    String conditionName = pc.getCondition().getConditionName();

                    String conditionDescription = switch (conditionName) {
                        case "환경" -> description.getEnvironmentDescription();
                        case "안전" -> description.getSafetyDescription();
                        case "편의" -> description.getConvenienceDescription();
                        case "소음" -> description.getNoiseDescription();
                        case "접근성" -> description.getAccessibilityDescription();
                        default -> "";
                    };

                    log.info(
                            "[Curation-Eval] rank={}, condition={}, weight={}, descNull={}",
                            rank,
                            conditionName,
                            weight,
                            (conditionDescription == null || conditionDescription.isBlank())
                    );

                    return new ConditionEvaluation(
                            rank,
                            conditionName,
                            weight,
                            conditionDescription
                    );
                })
                .toList();

        CurationEvaluationResult evaluationResult = new CurationEvaluationResult(propertyId, evaluations);
        String aiResult = openAiCurationService.generate(evaluationResult);
        return new CurationResponse(aiResult);
    }

}
