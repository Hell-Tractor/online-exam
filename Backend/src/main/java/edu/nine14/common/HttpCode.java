package edu.nine14.common;

public enum HttpCode {
    OK(200),
    UNAUTHORIZED(401),
    INTERNAL_SERVER_ERROR(500),
    BAD_REQUEST(400);
    private final int code;

    HttpCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
