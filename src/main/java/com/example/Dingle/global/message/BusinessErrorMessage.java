package com.example.Dingle.global.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorMessage implements DefaultErrorMessage {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 유저
    USER_NOT_EXISTS(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    DUPLICATE_USER_ID(HttpStatus.CONFLICT,"이미 사용중인 아이디입니다."),
    DUPLICATE_USER_EMAIL(HttpStatus.CONFLICT,"이미 사용중인 이메일입니다."),

    // 지역구, 선호 조건
    ALREADY_ONBOARDED(HttpStatus.CONFLICT, "온보딩은 최초 1회만 가능합니다."),
    DUPLICATE_DISTRICT(HttpStatus.BAD_REQUEST, "이미 등록된 지역구입니다."),
    DISTRICT_NOT_EXISTS(HttpStatus.NOT_FOUND, "등록되지 않은 지역구입니다."),
    DUPLICATE_CONDITION(HttpStatus.BAD_REQUEST, "이미 등록된 선호 조건입니다."),

    // 공인중개사
    REALTOR_NOT_EXISTS(HttpStatus.BAD_REQUEST, "등록되지 않은 공인중개사입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
