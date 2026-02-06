package com.example.Dingle.preference.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.preference.dto.request.ConditionRegisterDTO;
import com.example.Dingle.preference.service.ConditionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/condition")
@RestController
@RequiredArgsConstructor
public class ConditionController {

    private final ConditionService conditionService;

    @PostMapping("/register")
//    @Operation(summary = "선호 조건 등록 API", description = "선호 조건을 추가합니다.")
    public ResponseEntity<ResponseDTO<Void>> registerCondition(
            @RequestBody ConditionRegisterDTO request
    ) {
        conditionService.register(request);
        return ResponseEntity.ok(ResponseDTO.success(null));
    }
}
