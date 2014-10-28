-- name: find-by-creator
SELECT name, user_id creator
FROM groups
WHERE user_id = :user_id;

-- name: find-by-id
SELECT name, user_id creator
FROM groups
WHERE id = :id;

-- name: insert!
INSERT INTO groups (name, user_id creator)
VALUES (:name, :user_id);

-- name: add-user!
INSERT INTO users_groups (user_id, group_id)
VALUES (:user_id, :id);

-- name: find-by-user
SELECT name, user_id creator
FROM groups g
JOIN users_groups membership ON (
  membership.group_id  = g.id AND
  membership.user_id = :user_id
);
