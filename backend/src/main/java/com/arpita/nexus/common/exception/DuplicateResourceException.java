package com.arpita.nexus.common.exception;

public class DuplicateResourceException extends BusinessException{

    public DuplicateResourceException(String message){
        super(message, ErrorCode.DUPLICATE_RESOURCE);
    }
}
