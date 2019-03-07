package io.github.frapples.javademoandcookbook.springboot.common.vo;

import io.github.frapples.javademoandcookbook.springboot.common.exception.ErrorCode;
import io.github.frapples.javademoandcookbook.springboot.common.exception.ErrorCodeWrapperException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
* 1. 这是一个POJO类。要注意的一点是，如jackjson库序列化时，是需要getter的。
 *  */
@AllArgsConstructor
@Getter
public class ResponseVo<T> {

    private String code;
    private String msg;
    private String description;
    private T data;

    public static <T> ResponseVo<T> ofSuccess(T data) {
        ErrorCode errorCode = ErrorCode.SUCCESS;
        return new ResponseVo<T>(errorCode.getCode(), errorCode.getMessage(), "", data);
    }

    public static <T> ResponseVo<T> ofSystemError() {
        return ofFail(ErrorCode.SYSTEM_ERROR);
    }

    public static <T> ResponseVo<T> ofSystemError(String description) {
        return ofFail(ErrorCode.SYSTEM_ERROR, description);
    }

    public static <T> ResponseVo<T> ofFail(ErrorCode errorCode) {
        return ofFail(errorCode, "");
    }

    public static <T> ResponseVo<T> ofFail(ErrorCode errorCode, String description) {
        return new ResponseVo<T>(errorCode.getCode(), errorCode.getMessage(), description, null);
    }

    public static <T> ResponseVo<T> ofErroCodeWrapperException(ErrorCodeWrapperException e) {
        return ofFail(e.getErrorCode(), e.getDescription());
    }
}
