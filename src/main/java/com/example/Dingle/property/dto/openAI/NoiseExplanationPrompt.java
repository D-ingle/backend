package com.example.Dingle.property.dto.openAI;

import org.springframework.stereotype.Component;

@Component
public class NoiseExplanationPrompt {

    public String build(NoiseEvaluationResult result, String json) {
        return """
        너는 부동산의 소음과 유동인구 기반 활동성 점수를 설명하는 역할이다.

        매우 중요:
        - 숫자를 재계산하거나 점수를 수정하지 마라.
        - 비교 기준은 제공된 데이터만 사용하라.

        작성 규칙:
        - 반드시 한국어로 작성하라.
        - 정확히 2~3문장으로 작성하라.
        - 전체 길이는 200자 이내로 유지하라.
        - 소음과 유동인구가 주변 평균 대비 어떤 수준인지 설명하라.
        - 문장은 모두 ~입니다의 느낌으로 끝내라.

        아래 데이터를 바탕으로 activityScore가 %d점인 이유를 설명하라.
        activityScore는 종합 소음 점수로 표현해라

        데이터:
        %s
        """.formatted(result.getActivityScore(), json);
    }
}
