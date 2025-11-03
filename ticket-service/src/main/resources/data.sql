-- Passwords are 'admin' and 'user' respectively, hashed with BCrypt.
INSERT INTO users (id, username, password, role)
SELECT * FROM (
    SELECT 1 AS id, 'admin' AS username, '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqR2e5RzTTN.2Q21kdG/iM2xsX22' as password, 'ROLE_ADMIN' as role UNION ALL
    SELECT 2, 'user', '$2a$10$BDR0a2e505y.L2s9cK.qg.0kIe2N6.SM1V.1nB//R..J58E5z4PQW', 'ROLE_USER'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users);