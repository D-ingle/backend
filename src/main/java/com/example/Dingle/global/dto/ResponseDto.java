package com.example.Dingle.global.dto;

import com.example.Dingle.global.message.DefaultErrorMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseDto<T> {

    private boolean success;
    private T data;
    private ErrorDto error;

    public static <T> ResponseDto<T> success(final T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> fail(DefaultErrorMessage error) {
        return new ResponseDto<>(false, null, new ErrorDto(error.getMessage()));
    }
}
