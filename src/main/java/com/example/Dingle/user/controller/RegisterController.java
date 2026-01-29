package com.example.Dingle.user.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.user.dto.UserDTO;
import com.example.Dingle.user.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
@ResponseBody
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register/user")
    public ResponseEntity<ResponseDTO<Void>> register(@RequestBody UserDTO userDTO) {
        registerService.registerUser(userDTO);

        return ResponseEntity.ok(ResponseDTO.success(null));
    }

    @GetMapping("/auth/me")
    @Operation(summary = "사용자 확인 API", description = "요청을 보낸 사용자가 누구인지 확인합니다.")
    public String me(Authentication auth) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Main Controller : "+name;
    }
}
