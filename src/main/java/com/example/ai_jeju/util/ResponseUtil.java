package com.example.ai_jeju.util;

public class ResponseUtil {

    public static <T>ResponseDto<T> SUCCESS (T message, T data) {
        return new ResponseDto(ResponseStatus.SUCCESS, message, data);
    }
    public static <T>ResponseDto<T> FAILURE (T message, T data) {
        return new ResponseDto(ResponseStatus.FAILURE, message, data);
    }
    public static <T>ResponseDto<T> ERROR (T message, T data) {
        return new ResponseDto(ResponseStatus.ERROR, message, data);
    }
}