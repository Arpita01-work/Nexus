
package com.arpita.nexus.authentication.email;

import com.arpita.nexus.user.entity.User;

public interface EmailService {

    void sendActivationEmail(
        User user,
        String activationToken
    );
}
