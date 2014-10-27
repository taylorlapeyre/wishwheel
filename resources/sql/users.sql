-- name: find-by-email
SELECT id, email, first_name, last_name
FROM users
WHERE email = :email;

-- name: insert!
INSERT INTO users (email, password, first_name, last_name)
VALUES (:email, :password, :first_name, :last_name);


