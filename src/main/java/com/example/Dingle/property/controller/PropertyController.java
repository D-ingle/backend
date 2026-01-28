package com.example.Dingle.property.controller;

import com.example.Dingle.global.dto.ResponseDto;
import com.example.Dingle.property.dto.PropertyRegisterRequestDto;
import com.example.Dingle.property.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("/register")
    @Operation(summary = "매물 등록 API", description = "매물을 등록합니다.")
    public ResponseEntity<ResponseDto<Void>> register(
            @RequestBody PropertyRegisterRequestDto request
    ) {
        propertyService.register(request);
        return ResponseEntity.ok(ResponseDto.success(null));
    }
}
