package com.example.Dingle.user.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.user.dto.CustomUserDetails;
import com.example.Dingle.user.dto.DestinationDTO;
import com.example.Dingle.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/destination")
    public ResponseEntity<ResponseDTO<Void>> saveDestination(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody DestinationDTO destinationDTO) {
        userService.saveAndUpdate(customUserDetails.getUsername(), destinationDTO);
        return ResponseEntity.ok(ResponseDTO.success(null));
    }

    @GetMapping("/destination")
    public ResponseEntity<ResponseDTO<DestinationDTO>> getDestination(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        DestinationDTO response = userService.getDestination(customUserDetails.getUsername());
        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
