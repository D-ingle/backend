package com.example.Dingle.property.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.property.dto.PropertyListDTO;
import com.example.Dingle.property.dto.PropertyRegisterRequestDTO;
import com.example.Dingle.property.service.PropertyListService;
import com.example.Dingle.property.service.PropertyService;
import com.example.Dingle.user.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property")
public class PropertyController {

    private final PropertyService propertyService;
    private final PropertyListService propertyListService;

    @PostMapping("/register")
    @Operation(summary = "매물 등록 API", description = "매물을 등록합니다.")
    public ResponseEntity<ResponseDTO<Void>> register(
            @RequestBody PropertyRegisterRequestDTO request
    ) {
        propertyService.register(request);
        return ResponseEntity.ok(ResponseDTO.success(null));
    }

    @GetMapping("/recent")
    public ResponseEntity<ResponseDTO<List<PropertyListDTO>>> recentView(@RequestParam("propertyIds") List<Long> propertyIds) {

        List<PropertyListDTO> response = propertyListService.getPropertyList(propertyIds);
        return ResponseEntity.ok(ResponseDTO.success(response));
    }

    @GetMapping("/zzim")
    public ResponseEntity<ResponseDTO<List<PropertyListDTO>>> likeList(@AuthenticationPrincipal CustomUserDetails userDetails) {

        List<PropertyListDTO> response = propertyListService.getLikePropertyList(userDetails.getUsername());
        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
