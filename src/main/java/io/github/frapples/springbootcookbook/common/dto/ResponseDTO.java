package io.github.frapples.springbootcookbook.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
* 1. 这是一个POJO类。要注意的一点是，如jackjson库序列化时，是需要getter的。
 *  */
@AllArgsConstructor
@Getter
public class ResponseDTO<T> {

    public static final int SUCCESS_CODE = 200;
    public static final int FAILED_CODE = 500;

    private Integer code;
    private String msg;
    private T data;

    public static <T> ResponseDTO<T> ofSuccess(T data) {
        return new ResponseDTO<T>(SUCCESS_CODE, "执行成功", data);
    }

    public static <T> ResponseDTO<T> ofFailed() {
        return new ResponseDTO<T>(FAILED_CODE, "系统错误", null);
    }
}
