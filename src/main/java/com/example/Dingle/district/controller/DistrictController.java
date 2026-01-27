package com.example.Dingle.district.controller;

import com.example.Dingle.district.dto.request.DistrictRegisterDto;
import com.example.Dingle.district.service.DistrictService;
import com.example.Dingle.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/district")
@RestController
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @PostMapping("/register")
    @Operation(summary = "지역구 등록 API", description = "지역구를 추가합니다.")
    public ResponseEntity<ResponseDto<Void>> registerDistrict(
        @RequestBody DistrictRegisterDto request
    ) {
        districtService.register(request);
        return ResponseEntity.ok(ResponseDto.success(null));
    }
}
