package com.example.Dingle.district.controller;

import com.example.Dingle.district.dto.request.DistrictRegisterDTO;
import com.example.Dingle.district.service.DistrictService;
import com.example.Dingle.global.dto.ResponseDTO;
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
    public ResponseEntity<ResponseDTO<Void>> registerDistrict(
        @RequestBody DistrictRegisterDTO request
    ) {
        districtService.register(request);
        return ResponseEntity.ok(ResponseDTO.success(null));
    }
}
