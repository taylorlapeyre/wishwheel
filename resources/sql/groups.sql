-- name: find-by-user
SELECT name, user_id
FROM groups
WHERE user_id = :user_id;

-- name: find-by-id
SELECT name, user_id
FROM groups
WHERE id = :id;

-- name: insert!
INSERT INTO groups (name, user_id)
VALUES (:name, :user_id);

-- name: add-user!
INSERT INTO users_groups (user_id, group_id)
VALUES (:user_id, :id);
