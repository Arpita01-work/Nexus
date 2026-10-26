package com.arpita.nexus.authentication.repository;

import com.arpita.nexus.authentication.entity.AccountActivationToken;
import com.arpita.nexus.authentication.entity.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountActivationTokenRepository
    extends JpaRepository<AccountActivationToken, UUID> {

    Optional<AccountActivationToken> findByToken(String token);

    Optional<AccountActivationToken> findByUserId(UUID userId);

    boolean existsByToken(String token);

    long countByStatus(TokenStatus status);

}
