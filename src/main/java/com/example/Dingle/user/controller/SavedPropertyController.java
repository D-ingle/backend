package com.example.Dingle.user.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.user.dto.CustomUserDetails;
import com.example.Dingle.user.service.SavedPropertyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property")
public class SavedPropertyController {

    private final SavedPropertyService savedPropertyService;

    @PostMapping("/zzim/{propertyId}")
    @Operation(summary = "매물 찜 API", description = "매물을 찜합니다.")
    public ResponseEntity<ResponseDTO<Void>> saveProperty (
            @PathVariable Long propertyId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String userId = customUserDetails.getUsername();
        savedPropertyService.save(userId, propertyId);
        return ResponseEntity.ok(ResponseDTO.success(null));
    }

    @DeleteMapping("/zzim/{propertyId}")
    @Operation(summary = "매물 찜 취소 API", description = "매물 찜을 취소합니다.")
    public ResponseEntity<ResponseDTO<Void>> unsaveProperty(
            @PathVariable Long propertyId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String userId = customUserDetails.getUsername();
        savedPropertyService.unsave(userId, propertyId);
        return ResponseEntity.ok(ResponseDTO.success(null));
    }
}
