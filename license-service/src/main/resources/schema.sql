DROP TABLE IF EXISTS licenses;

CREATE TABLE licenses
(
    license_id        VARCHAR(36) PRIMARY KEY NOT NULL,
    organization_id   VARCHAR(36)             NOT NULL,
    license_type      VARCHAR(255)            NOT NULL,
    product_name      VARCHAR(255)            NOT NULL,
    license_max       INT                     NOT NULL,
    license_allocated INT,
    comment           VARCHAR(255)
);
