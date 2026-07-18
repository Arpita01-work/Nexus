package com.arpita.nexus.common.exception;

public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(message, ErrorCode.VALIDATION_ERROR);
    }
}
