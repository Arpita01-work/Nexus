CREATE TABLE account_activation_tokens
(
    id UUID PRIMARY KEY,

    token VARCHAR(255) NOT NULL,

    user_id UUID NOT NULL,

    status VARCHAR(20) NOT NULL,

    temporary_password_attempts INTEGER NOT NULL DEFAULT 0,

    expires_at TIMESTAMP NOT NULL,

    activated_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT,

    CONSTRAINT uk_activation_token
        UNIQUE (token),

    CONSTRAINT uk_activation_user
        UNIQUE (user_id),

    CONSTRAINT fk_activation_token_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);
