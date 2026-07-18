CREATE TABLE organizations
(
    id UUID PRIMARY KEY,

    name VARCHAR(150) NOT NULL,

    code VARCHAR(50) NOT NULL UNIQUE,

    email VARCHAR(255) NOT NULL UNIQUE,

    phone VARCHAR(20),

    website VARCHAR(255),

    description TEXT,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL
);

CREATE INDEX idx_organization_code
    ON organizations(code);

CREATE INDEX idx_organization_name
    ON organizations(name);