package com.example.Dingle.property.dto.openAI;

import org.springframework.stereotype.Component;

@Component
public class EnvironmentExplanationPrompt {
    public String build(EnvironmentEvaluationResult result, String json) {
        return """
          너는 이미 계산된 부동산 환경 점수를 설명하는 역할이다.
       
          매우 중요:
          - 숫자를 다시 계산하거나 점수를 수정하지 마라.
          - 점수 기준이나 계산 방식을 새로 해석하지 마라.
          - 제공된 데이터에 포함된 값만 사용하라.
        
          작성 규칙:
          - 반드시 한국어로 작성하라.
          - 정확히 2문장 또는 3문장으로 작성하라.
          - 전체 길이는 200자 이내로 유지하라.
          - 불릿 포인트나 목록을 사용하지 마라.
          - 공원과 산책로까지의 거리는 미터 단위로 자연스럽게 언급하라.
          - 경사도는 내부·외부 경사가 어떤 수준으로 평가되었는지 설명하되, 점수 구간을 직접 언급하지 마라.
          - 문장은 ~입니다로 끝내라.
        
          아래 데이터를 바탕으로 EnvironmentScore가 %d점인 이유를 구체적으로 설명하라.
        
          데이터:
          %s
        """.formatted(result.getEnvironmentScore(), json);
    }
}
