-- name: find-by-user
SELECT name, user_id, group_id
FROM wheels
WHERE wheels.user_id = :user_id;

-- name: find-by-group
SELECT name, user_id, group_id
FROM wheels
WHERE wheels.group_id = :group_id;

-- name: insert!
INSERT INTO wheels (name, user_id, group_id)
VALUES (:name, :user_id, :group_id);
