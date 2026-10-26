ALTER TABLE users
    ADD COLUMN role VARCHAR(30);

UPDATE users
SET role = 'EMPLOYEE'
WHERE role IS NULL;

ALTER TABLE users
    ALTER COLUMN role SET NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT chk_users_role
        CHECK (
            role IN (
                     'ORGANIZATION_ADMIN',
                     'MANAGER',
                     'EMPLOYEE'
                )
            );
