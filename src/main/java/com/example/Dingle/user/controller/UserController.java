package com.example.Dingle.user.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.global.message.AuthErrorMessage;
import com.example.Dingle.global.message.DefaultErrorMessage;
import com.example.Dingle.user.dto.CustomUserDetails;
import com.example.Dingle.user.dto.DestinationDTO;
import com.example.Dingle.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "자주 방문하는 스팟 저장 API", description = "자주 방문하는 스팟을 저장합니다.")
    public ResponseEntity<ResponseDTO<Void>> saveDestination(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody DestinationDTO destinationDTO) {
        if(customUserDetails == null){
            return ResponseEntity.status(AuthErrorMessage.USER_NOT_EXIST.getHttpStatus()).body(ResponseDTO.fail(AuthErrorMessage.USER_NOT_EXIST));
        }
        userService.saveAndUpdate(customUserDetails.getUsername(), destinationDTO);
        return ResponseEntity.ok(ResponseDTO.success(null));
    }

    @GetMapping("/destination")
    @Operation(summary = "자주 방문하는 스팟 조회 API", description = "자주 방문하는 스팟을 조회합니다.")
    public ResponseEntity<ResponseDTO<DestinationDTO>> getDestination(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        DestinationDTO response = userService.getDestination(customUserDetails.getUsername());
        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
