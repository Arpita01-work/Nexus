package com.arpita.nexus.authentication.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenGenerator {

    public String generateActivationToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
