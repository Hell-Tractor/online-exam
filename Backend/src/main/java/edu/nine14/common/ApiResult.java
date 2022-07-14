package edu.nine14.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装API的返回结果<br>
 * 请使用静态方法ok()与failed()构造对象<br>
 * Example:
 * <pre> return ApiResult.ok(someData).pack() </pre>
 * <pre> return ApiResult.failed(HttpCode.UNAUTHORIZED, "用户名或密码错误").pack() </pre>
 * @author 陈一鸣
 */
public class ApiResult<T> {
    private final HttpCode code;
    private final String message;
    private final T data;

    /**
     * 构造一个成功的结果
     * @param data 返回的数据
     */
    public static <T> Object ok(T data) {
        return (new ApiResult<>(HttpCode.OK, null, data)).pack();
    }

    /**
     * 构造一个失败的结果
     * @param code http状态码
     * @param message 失败的消息
     */
    public static <T> Object failed(HttpCode code, String message) {
        return (new ApiResult<>(code, message, null)).pack();
    }

    /**
     * 将结果打包为可以返回给客户端的json格式
     * @return Map对象用于序列化
     */
    protected Object pack() {
        Map<String, Object> result = new HashMap<>(2);
        if (code == HttpCode.OK) {
            result.put("code", code.getCode());
            result.put("data", data);
        } else {
            result.put("code", code.getCode());
            result.put("message", message);
        }
        return result;
    }

    protected ApiResult(HttpCode code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}