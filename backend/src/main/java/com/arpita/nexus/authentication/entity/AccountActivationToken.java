package com.arpita.nexus.authentication.entity;

import com.arpita.nexus.common.entity.BaseEntity;
import com.arpita.nexus.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
    name = "account_activation_tokens",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_activation_token",
            columnNames = "token"
        ),
        @UniqueConstraint(
            name = "uk_activation_user",
            columnNames = "user_id"
        )
    }
)
public class AccountActivationToken extends BaseEntity {

    @Column(nullable = false, length = 255)
    private String token;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_activation_token_user")
    )
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TokenStatus status = TokenStatus.PENDING;

    @Column(name = "temporary_password_attempts", nullable = false)
    private Integer temporaryPasswordAttempts = 0;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt;
}
