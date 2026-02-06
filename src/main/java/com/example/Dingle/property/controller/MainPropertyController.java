package com.example.Dingle.property.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.property.dto.MainPropertyResponseDTO;
import com.example.Dingle.property.dto.PropertySearchRequestDTO;
import com.example.Dingle.property.service.MainPropertyService;
import com.example.Dingle.property.type.PropertyType;
import com.example.Dingle.user.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class MainPropertyController {

    private final MainPropertyService mainPropertyService;

    public MainPropertyController(MainPropertyService mainPropertyService) {
        this.mainPropertyService = mainPropertyService;
    }

    @GetMapping("")
    @Operation(summary = "메인페이지 조회 API", description = "메인페이지를 조회합니다.")
    public ResponseEntity<ResponseDTO<MainPropertyResponseDTO>> getMainProperty(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(name = "select", required = false) List<Long> selectConditions,
            @RequestParam(name = "propertyType") PropertyType propertyType,
            @RequestParam(name = "cursor", required = false) Long cursor,
            @RequestParam(name = "size", required = false) Long size
    ) {

        MainPropertyResponseDTO response = mainPropertyService.getMainProperty(userDetails.getUsername(), selectConditions, propertyType, cursor, size);
        return ResponseEntity.ok(ResponseDTO.success(response));
    }

    @GetMapping("/search")
    @Operation(summary = "매물 검색 API", description = "매물을 검색합니다.")
    public ResponseEntity<ResponseDTO<MainPropertyResponseDTO>> searchProperty(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute PropertySearchRequestDTO requestDTO
    ) {

        MainPropertyResponseDTO response = mainPropertyService.searchProperty(userDetails.getUsername(), requestDTO);
        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
