package com.example.Dingle.property.service.openAI;

import com.example.Dingle.property.dto.openAI.CurationEvaluationResult;
import com.example.Dingle.property.dto.openAI.CurationPrompt;
import com.example.Dingle.property.util.OpenAiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAiCurationService {

    private final ObjectMapper objectMapper;
    private final OpenAiClient openAiClient;
    private final CurationPrompt curationPrompt;

    public String generate(CurationEvaluationResult result) {

        String json;
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            throw new RuntimeException(
                    "CurationEvaluationResult serialization failed", e
            );
        }

        log.info("[Curation-AI-JSON] {}", json);

        String prompt = curationPrompt.build(json);

        return openAiClient.generate(prompt);
    }
}
