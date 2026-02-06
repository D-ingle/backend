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
    INVALID_DESTINATION(HttpStatus.BAD_REQUEST, "목적지 정보가 올바르지 않습니다."),

    // 지역구, 선호 조건
    ALREADY_ONBOARDED(HttpStatus.CONFLICT, "온보딩은 최초 1회만 가능합니다."),
    CONDITION_NOT_EXISTS(HttpStatus.NOT_FOUND, "등록되지 않은 조건입니다."),
    DUPLICATE_DISTRICT(HttpStatus.BAD_REQUEST, "이미 등록된 지역구입니다."),
    DISTRICT_NOT_EXISTS(HttpStatus.NOT_FOUND, "등록되지 않은 지역구입니다."),
    DUPLICATE_CONDITION(HttpStatus.BAD_REQUEST, "이미 등록된 선호 조건입니다."),

    // 공인중개사
    REALTOR_NOT_EXISTS(HttpStatus.BAD_REQUEST, "등록되지 않은 공인중개사입니다."),

    // 매물
    ALREADY_ZZIMED(HttpStatus.CONFLICT,"이미 찜한 매물입니다."),
    PROPERTY_NOT_EXISTS(HttpStatus.NOT_FOUND, "매물을 찾을 수 없습니다."),
    NOT_ZZIMED(HttpStatus.CONFLICT,"찜한 매물이 아닙니다."),
    FORBIDDEN_PROPERTY_COMPARE(HttpStatus.FORBIDDEN, "찜한 매물만 비교할 수 있습니다."),
    EXCEED_COMPARE_LIMIT(HttpStatus.BAD_REQUEST, "비교는 최대 3개 매물까지만 가능합니다."),
    PROPERTY_SCORE_NOT_FOUND(HttpStatus.NOT_FOUND, "매물 점수를 찾을 수 없습니다."),
    PROPERTY_EXPLANATION_NOT_EXISTS(HttpStatus.NOT_FOUND, "매물 설명을 찾을 수 없습니다."),

    // 공공데이터
    CCTV_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CCTV 데이터 저장에 실패했습니다."),

    // GEOM
    GEOM_PARSE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "GEOM 파싱에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
