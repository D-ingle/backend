package com.example.Dingle.responseExample.controller;

import com.example.Dingle.global.dto.ResponseDto;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.responseExample.dto.TestResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping("/test/success")
    @Operation(summary = "테스트 API", description = "ResponseDto 테스트용 API")
    public ResponseEntity<ResponseDto<TestResponseDto>> testSuccess() {
        return ResponseEntity.ok(ResponseDto.success(new TestResponseDto("test success")));
    }

    @GetMapping("/test/fail")
    @Operation(summary = "테스트 API", description = "ResponseDto 테스트용 API")
    public ResponseEntity<ResponseDto<TestResponseDto>> testFail() {
        throw new BusinessException(BusinessErrorMessage.BAD_REQUEST);
    }
}
