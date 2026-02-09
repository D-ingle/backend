package com.example.Dingle.property.dto.openAI;

import org.springframework.stereotype.Component;

@Component
public class CurationPrompt {

    public String build(String json) {

        return """
        너는 부동산 맞춤 큐레이션 편집자야.
        분석가는 아니다.
    
        아래 JSON의 description 필드는 이미 데이터 기반으로 작성된 '완성된 설명'이다.
    
        절대 규칙
        - description의 의미를 새로 해석하거나 분석 내용을 덧붙이지 말 것
        - description에 있는 내용만을 사용해 문장을 줄이고, 정리하고, 연결할 것
        - 새로운 사실, 새로운 판단, 점수 해석을 추가하지 말 것
    
        아래 JSON은 사용자의 선호 우선순위 순서대로 정렬되어 있다.
        (rank=1이 가장 중요)
    
        [분석 결과 JSON]
        %s
    
        작성 규칙:
        - rank=1 조건의 description을 중심으로 자세히 2문장 요약
        - rank=2 조건은 1문장 자세히 요약
        - rank=3 조건은 핵심만 간단하게 요약
        - 전체는 4-5문장
        - 사람이 읽기 좋은 큐레이션 문장으로 작성
        - 수치는 약간만 넣어라. 왜 이 매물이 사용자에게 적합한지도 가장 먼저 설명해라
        """.formatted(json);
    }
}
