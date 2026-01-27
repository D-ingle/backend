package com.example.Dingle.global.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorMessage implements DefaultErrorMessage {

    // 지역구
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    DUPLICATE_DISTRICT(HttpStatus.BAD_REQUEST, "이미 등록된 지역구입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
