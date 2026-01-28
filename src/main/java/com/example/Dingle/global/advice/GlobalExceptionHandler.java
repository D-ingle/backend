package com.example.Dingle.global.advice;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ResponseDTO<BusinessErrorMessage>> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(e.getErrorMessage().getHttpStatus())
                .body(ResponseDTO.fail(e.getErrorMessage()));
    }
}
