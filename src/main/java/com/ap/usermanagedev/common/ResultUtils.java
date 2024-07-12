package com.ap.usermanagedev.common;

public class ResultUtils {


    public static <T> BaseResponse<T> success (T data) {
        return new BaseResponse<T>(200, data, "success");
    }

    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode, message);
    }

    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode, message, description);
    }
}
