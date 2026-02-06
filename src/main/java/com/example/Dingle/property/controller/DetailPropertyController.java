package com.example.Dingle.property.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.property.dto.DetailPropertyDTO;
import com.example.Dingle.property.service.DetailPropertyService;
import com.example.Dingle.user.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/property")
public class DetailPropertyController {

    private final DetailPropertyService detailPropertyService;

    public DetailPropertyController(DetailPropertyService detailPropertyService) {
        this.detailPropertyService = detailPropertyService;
    }

    @GetMapping("/{propertyId}")
    @Operation(summary = "매물 상세조회 API", description = "매물 상세조회를 합니다.")
    public ResponseEntity<ResponseDTO<DetailPropertyDTO>> getPropertyDetail(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("propertyId") Long propertyId) {
        DetailPropertyDTO response = detailPropertyService.getDetailProperty(userDetails.getUsername(), propertyId);

        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
