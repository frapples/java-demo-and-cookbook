package io.github.frapples.springbootcookbook.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/22
 */
@AllArgsConstructor
public enum ErrorCode {
    SUCCESS("SUCCESS", "执行成功"),
    RECORD_EXISTS("RECORD_EXISTS", "记录已存在"),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误");
    @Getter
    private String code;
    @Getter
    private String message;
}
