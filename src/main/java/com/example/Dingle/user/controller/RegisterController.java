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
    @Operation(summary = "회원가입 API", description = "회원을 등록합니다.")
    public ResponseEntity<ResponseDTO<Void>> register(@RequestBody UserDTO userDTO) {
        registerService.registerUser(userDTO);

        return ResponseEntity.ok(ResponseDTO.success(null));
    }

    @GetMapping("/auth/me")
    public String me(Authentication auth) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Main Controller : "+name;
    }
}
