INSERT INTO users(user_name, password, enabled)
VALUES ('john.carnell', '$2a$04$NX3QTkBJB00upxKeaKqFBeoIVc9JHvwVnj1lItxNphRj34wNx5wlu', true),
       ('william.woodward', '$2a$04$lM2hIsZVNYrQLi8mhvnTA.pheZtmzeivz6fyxCr9GZ6YSfP6YibCW', true);

INSERT INTO user_roles (user_name, role)
VALUES ('john.carnell', 'ROLE_USER'),
       ('william.woodward', 'ROLE_ADMIN'),
       ('william.woodward', 'ROLE_USER');

INSERT INTO user_orgs (organization_id, user_name)
VALUES ('d1859f1f-4bd7-4593-8654-ea6d9a6a626e', 'john.carnell'),
       ('42d3d4f5-9f33-42f4-8aca-b7519d6af1bb', 'william.woodward');