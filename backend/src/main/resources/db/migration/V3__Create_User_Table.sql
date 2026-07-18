CREATE TABLE users
(
    id UUID PRIMARY KEY,

    organization_id UUID NOT NULL,

    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),

    job_title VARCHAR(100),

    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT fk_user_organization
        FOREIGN KEY (organization_id)
            REFERENCES organizations(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_users_organization
    ON users(organization_id);

CREATE INDEX idx_users_email
    ON users(email);