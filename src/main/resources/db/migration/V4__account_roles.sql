CREATE TABLE account_roles (
    account_id BIGINT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (account_id, role_id)
);