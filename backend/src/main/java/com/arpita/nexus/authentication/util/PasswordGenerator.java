package com.arpita.nexus.authentication.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "@#$%&*!?";
    private static final String CHARACTERS =
        UPPERCASE + LOWERCASE + DIGITS + SPECIAL;

    private static final int PASSWORD_LENGTH = 12;

    private final SecureRandom random = new SecureRandom();

    public String generateTemporaryPassword() {

        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        password.append(randomCharacter(UPPERCASE));
        password.append(randomCharacter(LOWERCASE));
        password.append(randomCharacter(DIGITS));
        password.append(randomCharacter(SPECIAL));

        while (password.length() < PASSWORD_LENGTH) {
            password.append(randomCharacter(CHARACTERS));
        }

        return shuffle(password.toString());
    }

    private char randomCharacter(String source) {
        return source.charAt(random.nextInt(source.length()));
    }

    private String shuffle(String input) {

        char[] chars = input.toCharArray();

        for (int i = chars.length - 1; i > 0; i--) {

            int index = random.nextInt(i + 1);

            char temp = chars[index];
            chars[index] = chars[i];
            chars[i] = temp;
        }

        return new String(chars);
    }

}
