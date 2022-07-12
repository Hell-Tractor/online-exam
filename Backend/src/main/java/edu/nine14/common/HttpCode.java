package edu.nine14.common;

public enum HttpCode {
    OK(200, "OK"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    BAD_REQUEST(400, "BAD_REQUEST");
    private final int code;
    private final String message;

    HttpCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
