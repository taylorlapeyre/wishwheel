-- name: find-by-email
SELECT id, email, first_name, last_name, password, token
FROM users
WHERE email = :email;

-- name: find-by-token
SELECT id, email, first_name, last_name, password, token
FROM users
WHERE token = :token;

-- name: find-by-id
SELECT id, email, first_name, last_name, password, token
FROM users
WHERE id = :id;

-- name: insert!
INSERT INTO users (email, password, first_name, last_name)
VALUES (:email, :password, :first_name, :last_name);


