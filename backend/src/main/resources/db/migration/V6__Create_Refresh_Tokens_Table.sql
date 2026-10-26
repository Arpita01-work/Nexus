CREATE TABLE refresh_tokens (

                                id UUID PRIMARY KEY,

                                token VARCHAR(512) NOT NULL,

                                user_id UUID NOT NULL,

                                expires_at TIMESTAMP NOT NULL,

                                revoked BOOLEAN NOT NULL DEFAULT FALSE,

                                revoked_at TIMESTAMP,

                                created_at TIMESTAMP NOT NULL,

                                updated_at TIMESTAMP NOT NULL,

                                version BIGINT,

                                CONSTRAINT uk_refresh_token
                                    UNIQUE(token),

                                CONSTRAINT fk_refresh_token_user
                                    FOREIGN KEY(user_id)
                                        REFERENCES users(id)
                                        ON DELETE CASCADE
);
