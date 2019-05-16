package io.github.frapples.javademoandcookbook.springbootwebcore.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/22
 */
@AllArgsConstructor(staticName = "of")
public class ErrorCode {
    @Getter
    private String code;
    @Getter
    private String message;

    public APIException exception() {
        return new APIException(this);
    }

    public APIException exception(String description) {
        return new APIException(this, description);
    }

    public final static ErrorCode SUCCESS = ErrorCode.of("SUCCESS", "执行成功");
    public final static ErrorCode SYSTEM_ERROR = ErrorCode.of("SYSTEM_ERROR", "系统错误");
    public final static ErrorCode API_NOT_FOUND = ErrorCode.of("API_NOT_FOUND", "接口不存在");
}
