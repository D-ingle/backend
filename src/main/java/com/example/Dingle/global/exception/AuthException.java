package com.example.Dingle.global.exception;

import com.example.Dingle.global.message.DefaultErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthException extends RuntimeException {
    private final DefaultErrorMessage errorMessage;
}
