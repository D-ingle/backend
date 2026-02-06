package com.example.Dingle.property.dto.openAI;

import org.springframework.stereotype.Component;

@Component
public class ConvenienceExplanationPrompt {
    public String build(ConvenienceEvaluationResult result, String json) {
        return """
        너는 이미 계산된 부동산 편의 점수를 설명하는 역할이다.

        매우 중요:
        - 점수를 다시 계산하거나 변경하지 마라.
        - 기준을 새로 해석하지 마라.
        - 제공된 데이터만 사용하라.

        작성 규칙:
        - 반드시 한국어로 작성하라.
        - 정확히 2~3문장으로 작성하라.
        - 200자 이내로 작성하라.
        - 편의점, 마트·시장, 병원 접근성을 자연스럽게 언급하라.
        - 도보 기준 표현을 사용하라.
        - 문장은 ~입니다로 끝내라.

        아래 데이터를 바탕으로 ConvenienceScore가 %d점인 이유를 설명하라.

        데이터:
        %s
        """.formatted(result.getConvenienceScore(), json);
    }
}
