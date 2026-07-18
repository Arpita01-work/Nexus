package com.arpita.nexus.common.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private final boolean success;
    private final String message;
    private final List<String> errors;

    @Builder.Default
    private final LocalDateTime timestamp = LocalDateTime.now();
}
