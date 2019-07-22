package com.charlie.test.exception;

public enum CustomizeErrorCode {
    QUESTION_NOT_FOUND("你找的问题不存在,要不换个试试！"),
    CLIENT_ERROR("你这个请求错了吧，要不然换个姿势？"),
    SERVER_ERROR("服务冒烟了，稍后再试试吧！");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
