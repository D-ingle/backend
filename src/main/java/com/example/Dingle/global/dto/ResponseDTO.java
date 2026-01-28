package com.example.Dingle.global.dto;

import com.example.Dingle.global.message.DefaultErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDTO<T> {

    private boolean success;
    private T data;
    private ErrorDTO error;

    public static <T> ResponseDTO<T> success(final T data) {
        return new ResponseDTO<>(true, data, null);
    }

    public static <T> ResponseDTO<T> fail(DefaultErrorMessage error) {
        return new ResponseDTO<>(false, null, new ErrorDTO(error.getMessage()));
    }
}
