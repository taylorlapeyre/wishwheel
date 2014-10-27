-- name: find-by-user
SELECT name, user_id
FROM groups
WHERE user_id = :user_id;

-- name: insert!
INSERT INTO groups (name, user_id)
VALUES (:name, :user_id);
