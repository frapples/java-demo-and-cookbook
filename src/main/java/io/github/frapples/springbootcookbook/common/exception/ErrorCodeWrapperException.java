package io.github.frapples.springbootcookbook.common.exception;

import lombok.Getter;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/22
 *
 * 系统内部的错误传递（包括预料到的系统错误、没有预料到的系统错误、预料到的业务操作异常）使用该异常
 * 具体的错误类型使用ErrorCode区分
 * 当异常抛出至顶层（Controller）统一将其转换为前端错误码返回（手动处理也可，建议AOP）
 */
public class ErrorCodeWrapperException extends RuntimeException {

    @Getter
    ErrorCode errorCode;
    @Getter
    String description;

    public ErrorCodeWrapperException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCodeWrapperException(ErrorCode errorCode, String description) {
        super(description);
        this.description = description;
        this.errorCode = errorCode;
    }
}
