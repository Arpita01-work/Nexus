package com.arpita.nexus.common.exception;

public class ResourceNotFoundException extends BusinessException{

    public ResourceNotFoundException(String message){
        super(message, ErrorCode.RESOURCE_NOT_FOUND);
    }
}
