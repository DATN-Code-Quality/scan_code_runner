package org.hcmus.datn.common;

public enum ErrorCode {
    SUCCESS(0),
    FAILED(1),
    EXCEPTION(2),
    INVALID_PARAMS(3),
    NOT_FOUND(4);

    private final int value;

    ErrorCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ErrorCode fromValue(int value) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getValue() == value) {
                return errorCode;
            }
        }
        return null;
    }
}