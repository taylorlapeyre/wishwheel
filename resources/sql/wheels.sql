-- name: find-by-user
-- Selects all wheels that were created by the given user_id.
SELECT id, name, user_id, group_id
FROM wheels
WHERE .user_id = :user_id;

-- name: find-by-id
-- Selects all wheels that have the given id.
SELECT id, name, user_id, group_id
FROM wheels
WHERE id = :id;

-- name: find-by-group
-- Selects all wheels that belong to the given group.
SELECT id, name, user_id, group_id
FROM wheels
WHERE group_id = :group_id;

-- name: insert!
-- Inserts a new wish wheel into the database.
INSERT INTO wheels (name, user_id, group_id)
VALUES (:name, :user_id, :group_id);
