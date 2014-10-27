-- name: find-by-user
SELECT id, name, user_id, group_id
FROM wheels
WHERE .user_id = :user_id;

-- name: find-by-id
SELECT id, name, user_id, group_id
FROM wheels
WHERE id = :id;

-- name: find-by-group
SELECT id, name, user_id, group_id
FROM wheels
WHERE group_id = :group_id;

-- name: insert!
INSERT INTO wheels (name, user_id, group_id)
VALUES (:name, :user_id, :group_id);
