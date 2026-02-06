package com.example.Dingle.property.controller.openAI;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.property.dto.openAI.CurationResponse;
import com.example.Dingle.property.service.openAI.PersonalizedCurationService;
import com.example.Dingle.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/curation")
public class PersonalizedCurationController {

    private final PersonalizedCurationService curationService;

    @GetMapping("/{propertyId}")
    public ResponseEntity<ResponseDTO<CurationResponse>> curate(
            @PathVariable Long propertyId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CurationResponse response = curationService.curate(propertyId, userDetails.getUsername());

        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
