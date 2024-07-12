package com.ap.usermanagedev.exception;

import com.ap.usermanagedev.common.BaseResponse;
import com.ap.usermanagedev.common.ErrorCode;
import com.ap.usermanagedev.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    /**
     * 针对BusinessException异常做处理
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException:" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    /**
     * 针对BusinessException异常做处理
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse RuntimeExceptionHandler(BusinessException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
