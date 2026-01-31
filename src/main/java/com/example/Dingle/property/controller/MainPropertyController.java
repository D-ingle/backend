package com.example.Dingle.property.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.property.dto.MainPropertyResponseDTO;
import com.example.Dingle.property.service.MainPropertyService;
import com.example.Dingle.property.type.PropertyType;
import com.example.Dingle.user.dto.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class MainPropertyController {

    private final MainPropertyService mainPropertyService;

    public MainPropertyController(MainPropertyService mainPropertyService) {
        this.mainPropertyService = mainPropertyService;
    }

    @GetMapping("")
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
}
