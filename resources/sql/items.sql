-- name: find-by-wheel
SELECT id, name, price, wheel_id, user_id
FROM items
WHERE wheel_id = :wheel_id;

-- name: find-by-id
SELECT id, name, price, wheel_id, user_id
FROM items
WHERE id = :id;

-- name: insert-with-user!
INSERT INTO items (name, price, wheel_id, user_id)
VALUES (:name, :price, :wheel_id, :user_id);

-- name: insert!
INSERT INTO items (name, price, wheel_id)
VALUES (:name, :price, :wheel_id);

-- name: update!
UPDATE items
SET user_id = :user_id
WHERE id = :id;
