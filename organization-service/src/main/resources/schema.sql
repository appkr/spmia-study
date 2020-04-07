DROP TABLE IF EXISTS organizations;

CREATE TABLE organizations
(
    organization_id VARCHAR(36) PRIMARY KEY NOT NULL,
    name            VARCHAR(255)            NOT NULL,
    contact_name    VARCHAR(255)            NOT NULL,
    contact_email   VARCHAR(255)            NOT NULL,
    contact_phone   VARCHAR(255)            NOT NULL
);
