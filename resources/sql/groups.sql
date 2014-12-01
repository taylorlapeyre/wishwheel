-- name: find-by-creator
-- Selects all groups that were created by the user with the given id.
SELECT name, user_id
FROM groups
WHERE user_id = :user_id;

-- name: find-by-id
-- Selects all groups that match a given id.
SELECT name, user_id
FROM groups
WHERE id = :id;

-- name: insert!
-- Inserts a new group into the database.
INSERT INTO groups (name, user_id)
VALUES (:name, :user_id);

-- name: add-user!
-- Relates a given group id and a user id in the table users_groups.
INSERT INTO users_groups (user_id, group_id)
VALUES (:user_id, :id);

-- name: find-by-user
-- Given a user id, selects all groups that the user is a member of.
SELECT g.name, g.user_id
FROM groups g
JOIN users_groups membership ON (
  membership.group_id  = g.id AND
  membership.user_id = :user_id
);
