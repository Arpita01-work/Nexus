
package com.arpita.nexus.authentication.service;

import com.arpita.nexus.authentication.email.EmailService;
import com.arpita.nexus.authentication.entity.AccountActivationToken;
import com.arpita.nexus.authentication.entity.TokenStatus;
import com.arpita.nexus.authentication.repository.AccountActivationTokenRepository;
import com.arpita.nexus.authentication.util.TokenGenerator;
import com.arpita.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountActivationService {

    private static final int EXPIRY_HOURS = 24;

    private final AccountActivationTokenRepository repository;
    private final TokenGenerator tokenGenerator;
    private final EmailService emailService;


    @Transactional
    public void create(User user) {

        AccountActivationToken activationToken =
            new AccountActivationToken();

        activationToken.setUser(user);
        activationToken.setToken(
            tokenGenerator.generateActivationToken()
        );
        activationToken.setStatus(TokenStatus.PENDING);
        activationToken.setTemporaryPasswordAttempts(0);
        activationToken.setExpiresAt(
            LocalDateTime.now().plusHours(EXPIRY_HOURS)
        );

        AccountActivationToken savedToken =
            repository.save(activationToken);

        emailService.sendActivationEmail(
            user,
            savedToken.getToken()
        );
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markExpired(UUID tokenId) {
        AccountActivationToken token = repository.findById(tokenId)
            .orElseThrow(() ->
                new IllegalStateException("Activation token not found.")
            );

        if (token.getStatus() == TokenStatus.PENDING) {
            token.setStatus(TokenStatus.EXPIRED);
        }
    }
}
