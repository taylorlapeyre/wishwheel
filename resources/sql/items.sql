-- name: find-by-wheel
-- Selects all items that belong to a certain wish wheel.
SELECT id, name, price, wheel_id, user_id
FROM items
WHERE wheel_id = :wheel_id;

-- name: find-by-id
-- Selects all items that match a given id.
SELECT id, name, price, wheel_id, user_id
FROM items
WHERE id = :id;

-- name: insert-and-assign-user!
-- Inserts a new item into the database and assigns it to a user.
INSERT INTO items (name, price, wheel_id, user_id)
VALUES (:name, :price, :wheel_id, :user_id);

-- name: insert!
-- Inserts a new item into the database.
INSERT INTO items (name, price, wheel_id)
VALUES (:name, :price, :wheel_id);

-- name: assign-user!
-- Updates the item that matches the given id by assigning its user_id to
-- the user id given.
UPDATE items
SET user_id = :user_id
WHERE id = :id;
